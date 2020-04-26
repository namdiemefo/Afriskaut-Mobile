package com.naemo.afriskaut.views.fragments.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ProfileFragmentBinding
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.profilepicture.ProfilePic
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.utils.FragmentToolbar
import com.naemo.afriskaut.utils.ToolbarManager
import com.naemo.afriskaut.views.activities.account.login.LoginActivity
import com.naemo.afriskaut.views.activities.pages.radar.RadarActivity
import com.naemo.afriskaut.views.base.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>(),
    ProfileNavigator, MenuItem.OnMenuItemClickListener {

    var profileViewModel: ProfileViewModel? = null
        @Inject set

    var appPreferences = activity?.applicationContext?.let { AppPreferences(it)}
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    companion object {
        private const val PERMISSION_CODE = 1000
        private const val PROFILE_REQUEST_CODE = 1000
        const val RESULT_OK = -1
    }

    private var mLayoutId = R.layout.profile_fragment

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
        retrieveImage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doBinding()
        initViews()
        ToolbarManager(builder(), view).prepareToolbar()
    }

    fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.frag_toolbar)
            .withTitle(R.string.profile_menu)
            .withMenu(R.menu.nav_menu)
            .withMenuItems(mutableListOf(R.id.log_out), mutableListOf(this) )
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.log_out -> {
                logout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun logout() {
        appPreferences?.logout()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = profileViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) run {
            if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permission not granted request it
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                // SHOW POP UP FOR RUN TIME PERMISSIONS
                activity?.requestPermissions(permissions,
                    PERMISSION_CODE
                )
            } else {
                // permission is already granted
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun goToRadar() {
        startActivity(Intent(requireContext(), RadarActivity::class.java ))
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            PROFILE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
            } else {
                    appUtils.showSnackBar(requireContext(), profile_frame, "permission denied")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == PROFILE_REQUEST_CODE) {
            val uri = data?.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver?.query(uri!!, filePathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val imageString = cursor?.getString(columnIndex!!)
            cursor?.close()

            createImageData(imageString!!)
        }
    }

    private fun createImageData(imageString: String) {
        val file = File(imageString)
        processImage(file)
    }

    private fun processImage(file: File?) {
        val imageBody: RequestBody = RequestBody.create(MediaType.parse("image/png"), file!!)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, imageBody)
        uploadImage(image)
    }

    private fun uploadImage(image: MultipartBody.Part) {
        getViewModel()?.upload(image)
    }

    private fun retrieveImage() {
        getViewModel()?.saveImageFromNetwork()
        getViewModel()?.retrieveImage()?.observe(requireActivity(), Observer {
            setUpImage(it)
        })
    }

    private fun setUpImage(it: ProfilePic?) {
        val url = it?.data
        Glide.with(requireContext()).load(url).into(profile_image)
    }

    override fun showSpin() {
        appUtils.showDialog(requireContext())
    }

    override fun hideSpin() {
        appUtils.cancelDialog()
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils.showSnackBar(requireActivity().applicationContext, profile_frame, msg)
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        logout()
        return true
    }


}
