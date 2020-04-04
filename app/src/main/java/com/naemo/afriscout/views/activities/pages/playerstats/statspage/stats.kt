package com.naemo.afriscout.views.activities.pages.playerstats.statspage

import android.app.Application
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.player.stats.PlayerStatsRequest
import com.naemo.afriscout.api.models.player.stats.PlayerStatsResponse
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

class StatsPageViewModel(application: Application): BaseViewModel<StatsPageNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    fun getPlayerStats(id: Int) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val playerId = PlayerStatsRequest(id)

        val statsResponseCall: Call<PlayerStatsResponse> = client.getApi().stats(token, playerId)
        statsResponseCall.enqueue(object : Callback<PlayerStatsResponse> {
            override fun onResponse(call: Call<PlayerStatsResponse>, response: Response<PlayerStatsResponse>) {
                getNavigator()?.hideSpin()
                val stats: PlayerStatsResponse? = response.body()
                val data = stats?.data
                val statuscode = stats?.statuscode
                val msg = stats?.message
                if (statuscode == 200) {
                    getNavigator()?.showSnackBarMessage(msg!!)
                } else {
                    getNavigator()?.showSnackBarMessage(msg!!)
                }
            }

            override fun onFailure(call: Call<PlayerStatsResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    getNavigator()?.hideSpin()
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error")
                }
            }
        })
    }

}
interface StatsPageNavigator {

    fun goBack()

    fun pickClub()

    fun goToAllTime()

    fun showSnackBarMessage(msg: String)

    fun showSpin()

    fun hideSpin()
}

@Module
class StatsPageModule {
    @Provides
    fun providesStatsPageViewModel(application: Application): StatsPageViewModel {
        return StatsPageViewModel(
            application
        )
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_stats_page
    }
}