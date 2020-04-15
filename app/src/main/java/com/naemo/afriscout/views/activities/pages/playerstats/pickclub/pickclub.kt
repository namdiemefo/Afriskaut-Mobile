package com.naemo.afriscout.views.activities.pages.playerstats.pickclub

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.player.team.TeamNameRequest
import com.naemo.afriscout.api.models.player.team.TeamNameResponse
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.db.local.room.team.Team
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

    var appUtils = AppUtils()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    fun getTeamName(id: ArrayList<Int>?, type: ArrayList<String>?) {
        Log.d("stuff6", id?.toString()!!)
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val newIds = id.toSet().toList()
        Log.d("stuff7", newIds.toString())
        val teamNameRequest = TeamNameRequest(newIds)
        val map: Map<Int, String>? = newIds.zip(type!!).toMap()
        Log.d("stuff8", map.toString())

        val teamNameResponseCall: Call<TeamNameResponse> = client.getApi().team(token, teamNameRequest)
        teamNameResponseCall.enqueue(object : Callback<TeamNameResponse> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<TeamNameResponse>, response: Response<TeamNameResponse>) {
                getNavigator()?.hideSpin()
                val teamResponse = response.body()
                val teamData = teamResponse?.data
                val teamName = teamData?.teamNames
                val teamFlags = teamData?.teamFlags
                val teams = mutableMapOf<Int, String>()
                val flags = mutableMapOf<String, String>()
                val statusCode = teamResponse?.statuscode
                val msg = teamResponse?.message
                if (statusCode == 200) {
                    Log.d("baby", teamName.toString())
                    Log.d("baby2", teamFlags.toString())
                   teamName?.let {
                        val teamMap = newIds.zip(it).toMap()
                        teams.putAll(teamMap)
                       Log.d("baby3", teamMap.toString())
                    }

                    teamFlags?.let {
                        val flagMap = teamName?.zip(it)?.toMap()
                        flagMap?.let { it1 -> flags.putAll(it1) }
                        Log.d("baby4", flagMap.toString())
                    }

                    val team = Team(teams, flags)
                    getNavigator()?.retrieveTeams(team)

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

    fun retrieveTeams(team: Team?)

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