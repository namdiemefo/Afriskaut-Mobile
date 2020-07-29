package com.naemo.afriskaut.views.fragments.player.playerinfo

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.naemo.afriskaut.api.models.player.follow.FollowRequest
import com.naemo.afriskaut.api.models.player.follow.FollowResponse
import com.naemo.afriskaut.api.models.player.follow.UnFollowResponse
import com.naemo.afriskaut.api.models.player.follow.UnfollowRequest
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.following.FollowingRepository
import com.naemo.afriskaut.db.local.room.search.Player
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

class PlayerInfoViewModel(application: Application) : BaseViewModel<PlayerInfoNavigator>(application) {

    var image = ObservableField<String>()
    var fullName = ObservableField("")
    var weight = ObservableField("")
    var height = ObservableField("")
    var nationality = ObservableField("")
    var dob = ObservableField("")
    var pob = ObservableField("")
    var position = ObservableField("")
    var followBtn = ObservableField("")

    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    private var followingRepository: FollowingRepository? = null
    private var searchRepository: SearchRepository? = null


    init {
        followingRepository = FollowingRepository(application)
        searchRepository = SearchRepository(application)
    }

    fun fillFilelds(player: Player) {
        fullName.set(player.displayName)
        height.set(player.height)
        weight.set(player.weight)
        dob.set(player.birthdate)
        pob.set(player.birthplace)
        image.set(player.imagePath)
        nationality.set(player.nationality)
        position.set(player.position?.name)
        checkFollow(player.following)
    }

    private fun checkFollow(following: Boolean?) {
        Log.d("CHECK", "CHECKING FOLLOWING CALL")
        following?.let {
            if (it) {
                followBtn.set("Following")
            } else {
                followBtn.set("Follow")
            }
        }

    }

    fun follow(id: String) {
        Log.d("playerbio", id)
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = FollowRequest(id)
        val followResponseCall: Call<FollowResponse> = client.getApi().follow(token, request)
        followResponseCall.enqueue(object : Callback<FollowResponse> {
            override fun onResponse(call: Call<FollowResponse>, response: Response<FollowResponse>) {
                val follow: FollowResponse? = response.body()
                val statusCode = follow?.statuscode
                val message= follow?.message
                if (statusCode == 200) {
                    getNavigator()?.showSnackBarMessage(message!!)
                    followBtn.set("Following")
                    searchRepository?.updateFollowing(true, id)
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

    fun unfollow(id: String) {
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = UnfollowRequest(id)
        val unfollowResponseCall: Call<UnFollowResponse> = client.getApi().unfollow(token, request)
        unfollowResponseCall.enqueue(object : Callback<UnFollowResponse> {
            override fun onResponse(call: Call<UnFollowResponse>, response: Response<UnFollowResponse>) {
                val unfollow: UnFollowResponse? = response.body()
                val statusCode = unfollow?.statuscode
                val msg = unfollow?.message
                if (statusCode == 200) {
                    getNavigator()?.showSnackBarMessage(msg!!)
                    followBtn.set("Follow")
                    searchRepository?.updateFollowing(false, id)
                } else {
                    getNavigator()?.showSnackBarMessage(msg!!)
                }
            }

            override fun onFailure(call: Call<UnFollowResponse>, t: Throwable) {
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }
        })
    }

}

interface PlayerInfoNavigator {

    fun showSnackBarMessage(msg: String)

    fun goBack()

    fun showSpin()

    fun hideSpin()

    fun moveToPickStats()

    fun followPlayer()

}

@Module
class PlayerInfoModule {

    @Provides
    fun providesPlayerInfoViewModel(application: Application): PlayerInfoViewModel {
        return PlayerInfoViewModel(application)
    }
}