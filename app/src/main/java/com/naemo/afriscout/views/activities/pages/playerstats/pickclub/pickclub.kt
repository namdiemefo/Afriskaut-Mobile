package com.naemo.afriscout.views.activities.pages.playerstats.pickclub

import android.app.Application
import android.util.Log
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.player.team.TeamNameRequest
import com.naemo.afriscout.api.models.player.team.TeamNameResponse
import com.naemo.afriscout.db.local.preferences.AppPreferences
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

class PickClubViewModel(application: Application): BaseViewModel<PickClubNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    fun getTeamName(array: ArrayList<Int>?, type: ArrayList<String>?) {
        Log.d("stuff7", array?.toString()!!)
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val teamNameRequest = TeamNameRequest(array)
        val map: Map<Int, String>? = array.zip(type!!).toMap()

        val teamNameResponseCall: Call<TeamNameResponse> = client.getApi().team(token, teamNameRequest)
        teamNameResponseCall.enqueue(object : Callback<TeamNameResponse> {
            override fun onResponse(call: Call<TeamNameResponse>, response: Response<TeamNameResponse>) {
                getNavigator()?.hideSpin()
                val teamResponse = response.body()
                val teamName = teamResponse?.data
                val statusCode = teamResponse?.statuscode
                val msg = teamResponse?.message
                if (statusCode == 200) {
                    teamName?.let {
                        for (i in it) {
                            val name = i
                            if (type!!.equals("domestic"))
                            Log.d("teamName", name)
                        }
                    }
                } else {
                    getNavigator()?.showSnackBarMessage(msg!!)
                }

            }

            override fun onFailure(call: Call<TeamNameResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }
        })
    }

}

interface PickClubNavigator {

    fun goBack()

    fun showSpin()

    fun hideSpin()

    fun showSnackBarMessage(msg: String)
}

@Module
class PickClubModule {

    @Provides
    fun providesPickClubViewModel(application: Application): PickClubViewModel {
        return PickClubViewModel(
            application
        )
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_pick_club_page
    }
}