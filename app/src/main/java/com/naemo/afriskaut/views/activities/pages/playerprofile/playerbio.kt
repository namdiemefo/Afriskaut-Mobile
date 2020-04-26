package com.naemo.afriskaut.views.activities.pages.playerprofile

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.player.follow.FollowResponse
import com.naemo.afriskaut.api.models.player.follow.UnfollowResponse
import com.naemo.afriskaut.api.models.player.profile.ProfileRequest
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.follow.FollowRepository
import com.naemo.afriskaut.db.local.room.following.FollowingRepository
import com.naemo.afriskaut.db.local.room.search.SearchRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseViewModel
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
    private var searchRepository: SearchRepository? = null


    init {
        repository = FollowRepository(application)
        followingRepository = FollowingRepository(application)
        searchRepository = SearchRepository(application)
    }

    fun makeCall(img: String, name: String, playerHeight: String, playerDob: String, team: String, playerNationality: String, playerPosition: String, following: Boolean) {
        fullName.set(name)
        height.set(playerHeight)
        dob.set(playerDob)
        club.set(team)
        image.set(img)
        nationality.set(playerNationality)
        position.set(playerPosition)
        checkFollow(following)
    }

    private fun checkFollow(following: Boolean) {
        Log.d("CHECK", "CHECKING FOLLOWING CALL")
        if (following) {
            followBtn.set("Following")
        } else {
            followBtn.set("Follow")
        }
    }

    fun follow(id: String, dbId: Int) {
        Log.d("playerbio", id)
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = ProfileRequest(id)
        val followResponseCall: Call<FollowResponse> = client.getApi().follow(token, request)
        followResponseCall.enqueue(object : Callback<FollowResponse> {
            override fun onResponse(call: Call<FollowResponse>, response: Response<FollowResponse>) {
                val follow: FollowResponse? = response.body()
                val statusCode = follow?.statuscode
                val message= follow?.message
                if (statusCode == 200) {
                    getNavigator()?.showSnackBarMessage(message!!)
                    followBtn.set("Following")
                    searchRepository?.updateFollowing(true, dbId)
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

    fun unfollow(id: String, dbId: Int) {
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = ProfileRequest(id)
        val unfollowResponseCall: Call<UnfollowResponse> = client.getApi().unfollow(token, request)
        unfollowResponseCall.enqueue(object : Callback<UnfollowResponse> {
            override fun onResponse(call: Call<UnfollowResponse>, response: Response<UnfollowResponse>) {
                val unfollow: UnfollowResponse? = response.body()
                val statusCode = unfollow?.statuscode
                val msg = unfollow?.message
                if (statusCode == 200) {
                    getNavigator()?.showSnackBarMessage(msg!!)
                    followBtn.set("Follow")
                    searchRepository?.updateFollowing(false, dbId)
                } else {
                    getNavigator()?.showSnackBarMessage(msg!!)
                }
            }

            override fun onFailure(call: Call<UnfollowResponse>, t: Throwable) {
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

    fun goToStatsPage()
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