package com.naemo.afriskaut.views.fragments.profile

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.profile.ProfileImageResponse
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.profilepicture.ProfilePic
import com.naemo.afriskaut.db.local.room.profilepicture.ProfilePicRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named



class ProfileViewModel(application: Application) : BaseViewModel<ProfileNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    private var repository: ProfilePicRepository? = null
    val TAG = "ProfilePicViewModel"

    init {
        repository =
            ProfilePicRepository(application)
    }


    var fullName = ObservableField("")
    var role = ObservableField("")
    var imageUrl = ObservableField<String>()


    fun setUpProfile() {
        val user = appPreferences.getUser()
        val name = user.firstName.plus(" ").plus(user.lastName)
        Log.d("viewModel", name)
        val profileRole = user.role
        fullName.set(name)
        role.set(profileRole)
    }

    fun upload(image: MultipartBody.Part) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"

        val uploadResponseCall: Call<ProfileImageResponse> = client.getApi().upload(token, image)
        uploadResponseCall.enqueue(object : Callback<ProfileImageResponse> {
            override fun onResponse(call: Call<ProfileImageResponse>, response: Response<ProfileImageResponse>) {
                getNavigator()?.hideSpin()
                val imageResponse = response.body()
                val resCode = imageResponse?.statuscode
                val msg = imageResponse?.message
                val img = imageResponse?.data?.filename
                if (resCode == 200) {
                    imageUrl.set(img)
                    getNavigator()?.showSnackBarMessage(msg!!)
                } else {
                    getNavigator()?.showSnackBarMessage("please try again with the correct file type")
                }
            }

            override fun onFailure(call: Call<ProfileImageResponse>, t: Throwable) {
                if (t is IOException) {
                    getNavigator()?.hideSpin()
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }

        })
    }

    fun retrieveImage(): LiveData<ProfilePic>? {
        Log.d(TAG, "GETTING THE IMAGE")
        return repository?.loadTheImage()
    }

    fun saveImageFromNetwork() = CoroutineScope(Main).launch {
        Log.d(TAG, "PUTTING THE IMAGE")
        repository?.saveTheImage()
    }


}

interface ProfileNavigator {

    fun openGallery()

    fun goToRadar()

    fun showSnackBarMessage(msg: String)

    fun showSpin()

    fun hideSpin()
}

@Module
class ProfileModule {

    @Provides
    fun provideProfileViewModel(application: Application): ProfileViewModel {
        return ProfileViewModel(application)
    }

    @Provides
    @Named("profile")
    fun layoutId(): Int {
          return R.layout.profile_fragment
    }


}
