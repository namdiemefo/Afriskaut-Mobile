package com.naemo.afriscout.views.activities.pages.playerprofile

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.player.follow.FollowResponse
import com.naemo.afriscout.api.models.player.follow.FollowingResponse
import com.naemo.afriscout.api.models.player.profile.ProfileRequest
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.db.local.room.follow.FollowRepository
import com.naemo.afriscout.db.local.room.following.FollowingRepository
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
    private var followingRepository: FollowingRepository? = null

    init {
        repository = FollowRepository(application)
        followingRepository = FollowingRepository(application)
    }

    fun makeCall(id: String, img: String, name: String, playerHeight: String, playerDob: String, team: String, playerNationality: String, playerPosition: String, following: Boolean) {
        fullName.set(name)
        height.set(playerHeight)
        dob.set(playerDob)
        club.set(team)
        image.set(img)
        nationality.set(playerNationality)
        position.set(playerPosition)
        checkFollow(following)
    /*    getNavigator()?.showSpin()
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
                if (statusCode == 200) {
                    Log.d("CHECK", "ABOUT TO MAKE FOLLOWING CALL")
                    makeFollowing(id)

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

        })*/
    }

    private fun makeFollowing(id: String) {
        makeFollowingCall(id)

    }

    private fun makeFollowingCall(id: String) {
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val followingResponseCall: Call<FollowingResponse> = client.getApi().following(token)
        followingResponseCall.enqueue(object : Callback<FollowingResponse> {
            override fun onResponse(call: Call<FollowingResponse>, response: Response<FollowingResponse>) {
                val followingResponse: FollowingResponse? = response.body()
                val statusCode = followingResponse?.statuscode
                val data = followingResponse?.data
                if (statusCode == 200) {
                    Log.d("CHECK", "ABOUT TO SAVE FOLLOWING CALL")
                    followingRepository?.save(data!!)
                    Log.d("CHECK", "ABOUT TO CHECK FOLLOWING CALL")
                    //checkFollow(id)
                } else {
                    getNavigator()?.showSnackBarMessage("wetin happen")
                }
            }

            override fun onFailure(call: Call<FollowingResponse>, t: Throwable) {
                if (t is IOException) {
                    getNavigator()?.hideSpin()
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }


        })
    }

    private fun checkFollow(following: Boolean){
        Log.d("CHECK", "CHECKING FOLLOWING CALL")
       //val check = followingRepository?.search(id)
        //Log.d("CHECK", check.toString())
        if (following) {
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
                if (statusCode == 200) {
                    getNavigator()?.showSnackBarMessage(message!!)
                    followBtn.set("Following")
                } else {
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                if (t is IOException) {
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
        return PlayerProfileViewModel(
            application
        )
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_player_profile
    }
}