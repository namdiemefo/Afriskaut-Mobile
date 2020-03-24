package com.naemo.afriscout.views.activities.pages

import android.app.Application
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.player.follow.FollowResponse
import com.naemo.afriscout.api.models.player.profile.ProfileRequest
import com.naemo.afriscout.api.models.player.profile.ProfileResponse
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.db.local.room.follow.FollowRepository
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PlayerProfileViewModel(application: Application) : BaseViewModel<PlayerProfileNavigator>(application) {

    var image = ObservableField<String>()
    var fullName = ObservableField("")
    var foot = ObservableField("")
    var height = ObservableField("")
    var nationality = ObservableField("")
    var dob = ObservableField("")
    var club = ObservableField("")
    var position = ObservableField("")
    var followBtn = ObservableField("")

    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    private var repository: FollowRepository? = null

    init {
        repository = FollowRepository(application)
    }

    fun makeCall(id: String) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val profile = ProfileRequest(id)
        val profileResponseCall: Call<ProfileResponse> = client.getApi().profile(token, profile)
        profileResponseCall.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                getNavigator()?.hideSpin()
                val player: ProfileResponse? = response.body()
                val playerData = player?.data
                val statusCode = player?.statuscode
                //Log.d("no enter", playerData?.fullname!!)
                if (statusCode == 200) {
                    checkFollow(id)
                  //  Log.d("enter", playerData?.fullname!!)
                    val name = playerData?.fullname
                    val playerHeight = playerData?.height
                    val country = playerData?.nationality
                    val date = playerData?.dob
                    val currentPosition = playerData?.position
                    val team = playerData?.team
                    val playerImage = playerData?.image
                    fullName.set(name)
                    height.set(playerHeight)
                    nationality.set(country)
                    dob.set(date)
                    club.set(team)
                    position.set(currentPosition)
                    image.set(playerImage)

                } else {
                    getNavigator()?.showSnackBarMessage("server error")
                }

            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    getNavigator()?.hideSpin()
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }

        })
    }

    private fun checkFollow(id: String){
       val check = repository?.search(id)
        if (check == true) {
            followBtn.set("Following")
        } else {
            followBtn.set("Follow")
        }
    }

    fun follow(id: String) {
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val profile = ProfileRequest(id)
        val followResponseCall: Call<FollowResponse> = client.getApi().follow(token, profile)
        followResponseCall.enqueue(object : Callback<FollowResponse> {
            override fun onResponse(call: Call<FollowResponse>, response: Response<FollowResponse>) {
                val follow: FollowResponse? = response.body()
                val statusCode = follow?.statuscode
                val message= follow?.message
                val data =  follow?.data
                if (statusCode == 200) {
                    getNavigator()?.showSnackBarMessage(message!!)
                    followBtn.set("Following")
                    repository?.save(data!!)

                } else {
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                if (t is IOException) {
                    getNavigator()?.hideSpin()
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }


        })

    }

}

interface PlayerProfileNavigator {

    fun showSnackBarMessage(msg: String)

    fun showSpin()

    fun hideSpin()

    fun followPlayer()

    fun goBack()
}

@Module
class PlayerProfileModule {

    @Provides
    fun providesPlayerProfileViewModel(application: Application): PlayerProfileViewModel {
        return PlayerProfileViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_player_profile
    }
}