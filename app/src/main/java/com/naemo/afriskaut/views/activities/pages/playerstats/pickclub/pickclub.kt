package com.naemo.afriskaut.views.activities.pages.playerstats.pickclub

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.player.league.LeagueNameRequest
import com.naemo.afriskaut.api.models.player.league.LeagueNameResponse
import com.naemo.afriskaut.api.models.player.season.SeasonNameRequest
import com.naemo.afriskaut.api.models.player.season.SeasonNameResponse
import com.naemo.afriskaut.api.models.player.team.TeamNameRequest
import com.naemo.afriskaut.api.models.player.team.TeamNameResponse
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.stats.StatsRepository
import com.naemo.afriskaut.db.local.room.team.Team
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

class PickClubViewModel(application: Application): BaseViewModel<PickClubNavigator>(application) {

    var appUtils = AppUtils()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    private var repository: StatsRepository? = null

    init {
        repository = StatsRepository(application)
    }


    fun getTeamName(id: ArrayList<Int>?) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val newIds = id?.toSet()?.toList()
        val teamNameRequest = TeamNameRequest(newIds)

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

                if (statusCode == 200) {

                   teamName?.let {
                        val teamMap = newIds?.zip(it)?.toMap()
                        teamMap?.let { it1 -> teams.putAll(it1) }

                    }

                    teamFlags?.let {
                        val flagMap = teamName?.zip(it)?.toMap()
                        flagMap?.let { it1 -> flags.putAll(it1) }
                    }

                    val team = Team(teams, flags)
                    getNavigator()?.retrieveTeams(team)

                } else {
                    getNavigator()?.showSnackBarMessage("server error")
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

    fun getSeasonName(id: ArrayList<Int>?) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val newIds = id?.toSet()?.toList()
        Log.d("season2", newIds.toString())
        val who = arrayListOf<Int>(1929,1934)
        val seasonNameRequest = SeasonNameRequest(who)

        val seasonNameResponseCall: Call<SeasonNameResponse> = client.getApi().season(token, seasonNameRequest)
        seasonNameResponseCall.enqueue(object : Callback<SeasonNameResponse> {
            override fun onResponse(call: Call<SeasonNameResponse>, response: Response<SeasonNameResponse>) {
                getNavigator()?.hideSpin()
                val season = mutableMapOf<Int, String>()
                val seasonResponse = response.body()
                val statusCode = seasonResponse?.statuscode
                val data = seasonResponse?.data
                if (statusCode == 200) {
                   data?.let {
                       val seasonMap = id?.zip(it)?.toMap()
                       seasonMap?.let { it1 -> season.putAll(it1) }
                   }
                    getNavigator()?.retrieveSeason(season)
                } else {
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }

            override fun onFailure(call: Call<SeasonNameResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }
        })
    }


    fun getLeagueName(id: ArrayList<Int>?) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val newIds = id?.toSet()?.toList()
        val who = arrayListOf<Int>(271, 513)
        val leagueNameRequest = LeagueNameRequest(who)

        val leagueNameResponseCall: Call<LeagueNameResponse> = client.getApi().league(token, leagueNameRequest)
        leagueNameResponseCall.enqueue(object : Callback<LeagueNameResponse> {
            override fun onResponse(call: Call<LeagueNameResponse>, response: Response<LeagueNameResponse>) {
                getNavigator()?.hideSpin()
                val leagueId = mutableMapOf<Int, String>()
                val leagueLogo = mutableMapOf<String, String>()
                val seasonResponse = response.body()
                val statusCode = seasonResponse?.statuscode
                val data = seasonResponse?.data
                val logo = data?.leagueLogo
                val name = data?.leagueName
                if (statusCode == 200) {
                    logo?.let {
                        val logoName = name?.zip(it)?.toMap()
                        logoName?.let { it1 -> leagueLogo.putAll(it1) }
                    }

                    name?.let {
                        val idName = id?.zip(it)?.toMap()
                        idName?.let { it1 -> leagueId.putAll(it1) }
                    }
                    val team = Team(leagueId, leagueLogo)
                    getNavigator()?.retrieveLeagues(team)

                } else {
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }

            override fun onFailure(call: Call<LeagueNameResponse>, t: Throwable) {
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

    fun retrieveLeagues(team: Team?)

    fun retrieveSeason(seasonName: Map<Int, String>?)

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