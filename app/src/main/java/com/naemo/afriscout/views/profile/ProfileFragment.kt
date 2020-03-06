package com.naemo.afriscout.views.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.naemo.afriscout.BR

import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.profile.ProfileImageResponse
import com.naemo.afriscout.api.models.profile.RetrieveImageResponse
import com.naemo.afriscout.databinding.ProfileFragmentBinding
import com.naemo.afriscout.db.local.AppPreferences
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.HashMap
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>(), ProfileNavigator {

    var profileViewModel: ProfileViewModel? = null
        @Inject set

    var appPreferences = activity?.applicationContext?.let { AppPreferences(it) }
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    var client = Client()
        @Inject set

    companion object {
        private const val PERMISSION_CODE = 1000
        private const val PROFILE_REQUEST_CODE = 1000
        private var imageData: ByteArray? = null
        private var bitmap: Bitmap? = null
        const val RESULT_OK = -1
    }

    //@Named("profile")
    var mLayoutId = R.layout.profile_fragment
       // @Inject set
    var mBinder: ProfileFragmentBinding? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): ProfileViewModel? {
        return profileViewModel
    }

    private fun initViews() {
        getViewModel()?.setUpProfile()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doBinding()
        initViews()
    }


    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = profileViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
        retrieveImage()
    }

    override fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) run {
            if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permission not granted request it
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                // SHOW POP UP FOR RUN TIME PERMISSIONS
                activity?.requestPermissions(permissions, PERMISSION_CODE)
            } else {
                // permission is already granted
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PROFILE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
            } else {
                    appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, "permission denied")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == PROFILE_REQUEST_CODE) {
            val uri = data?.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = activity?.contentResolver?.query(uri!!, filePathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val imageString = cursor?.getString(columnIndex!!)

            createImageData(imageString!!)
           // profile_image.setImageURI(data?.data)
        }
    }

    private fun createImageData(imageString: String) {
        Log.d("image", imageString)
        val file = File(imageString)
        processImage(file)
    }

    private fun processImage(file: File?) {
        val imageBody: RequestBody = RequestBody.create(MediaType.parse("image/png"), file!!)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, imageBody)
        uploadImage(image)
    }

    private fun uploadImage(image: MultipartBody.Part) {
        val user = appPreferences?.getUser()
        val userToken = user?.jwt_token
        val token = "Bearer $userToken"
        val headers = HashMap<String, String>()

        headers["Authorization"] = token
        val uploadResponseCall: Call<ProfileImageResponse> = client.getApi().upload(token, image)
        uploadResponseCall.enqueue(object : retrofit2.Callback<ProfileImageResponse> {
            override fun onResponse(call: Call<ProfileImageResponse>, response: Response<ProfileImageResponse>) {
                val imageResponse = response.body()
                val resCode = imageResponse?.statuscode
                val msg = imageResponse?.message
                val img = imageResponse?.data?.filename
                if (resCode == 200) {
                    appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, msg!!)
                    Glide.with(requireActivity()).load(img).into(profile_image)
                } else {
                    appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, "wrong file type")
                }
            }

            override fun onFailure(call: Call<ProfileImageResponse>, t: Throwable) {
                if (t is IOException) {
                    call.cancel()
                    Log.d("profilefragment", "issue")
                    appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, "server error")
                }
            }
        })

    }

    private fun retrieveImage() {
        val user = appPreferences?.getUser()
        val userToken = user?.jwt_token
        val token = "Bearer $userToken"

        val retrieveResponseCall: Call<RetrieveImageResponse> = client.getApi().retrieve(token)
        retrieveResponseCall.enqueue(object : retrofit2.Callback<RetrieveImageResponse> {
            override fun onResponse(call: Call<RetrieveImageResponse>, response: Response<RetrieveImageResponse>) {
                val retrieveResponse = response.body()
                val resCode = retrieveResponse?.statuscode
                val msg = retrieveResponse?.message
                val img = retrieveResponse?.data
                if (resCode == 200) {
                    Glide.with(requireActivity()).load(img).into(profile_image)
                } else {
                    appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, msg!!)
                }
            }

            override fun onFailure(call: Call<RetrieveImageResponse>, t: Throwable) {
                if (t is IOException) {
                    call.cancel()
                    appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, "server error")
                }
            }
        })
    }


}
