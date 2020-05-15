package com.naemo.afriskaut.db.local.room.profilepicture

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.views.fragments.profile.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProfilePicRepository(application: Application): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val TAG = "repository"
    var profilePicDao: ProfilePicDao?  = null
    val profileViewModel: ProfileViewModel? = null
    val context: Context? = null
    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    init {
        val dataBase =
            ProfilePicDataBase.invoke(
                application
            )
        profilePicDao = dataBase.profilepicdao()
    }

    fun loadTheImage(): LiveData<ProfilePic>? {
        return profilePicDao?.loadImage()
    }

    fun saveTheImage() {
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"

        val profilePicCall: Call<ProfilePic> = client.getApi().retrieveImage(token)
        profilePicCall.enqueue(object : Callback<ProfilePic> {
            override fun onResponse(call: Call<ProfilePic>, response: Response<ProfilePic>) {
                val picResponse: ProfilePic? = response.body()
                val statusCode = picResponse?.statuscode
                if (statusCode == 200) {
                    launch {
                        val image = response.body()
                        save(image)
                    }
                } else {
                    profileViewModel?.getNavigator()?.showSnackBarMessage("server error")
                }
            }

            override fun onFailure(call: Call<ProfilePic>, t: Throwable) {
                profileViewModel?.getNavigator()?.showSnackBarMessage("server error")
            }
        })
    }

    private suspend fun save(image: ProfilePic?) {
        withContext(IO) {
            profilePicDao?.deleteImage()
            profilePicDao?.saveImage(image!!)
        }
    }


}