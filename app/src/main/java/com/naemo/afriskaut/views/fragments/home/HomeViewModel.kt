package com.naemo.afriskaut.views.fragments.home

import android.app.Application
import androidx.databinding.ObservableField
import com.naemo.afriskaut.api.models.search.ScoutPlayerRequest
import com.naemo.afriskaut.api.models.search.ScoutPlayerResponse
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.scout.ScoutRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class HomeViewModel(application: Application) : BaseViewModel<HomeNavigator>(application) {

    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    private var repository: ScoutRepository = ScoutRepository(application)

    var age = ObservableField("")

    fun scoutPlayer(nation: String?, position: String?) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"

        val playerAge = age.get()
        val scoutRequest = ScoutPlayerRequest(nation, position, playerAge)

        val scoutCall: Call<ScoutPlayerResponse> = client.getApi().scout(token, scoutRequest)
        scoutCall.enqueue(object : Callback<ScoutPlayerResponse> {
            override fun onResponse(call: Call<ScoutPlayerResponse>, response: Response<ScoutPlayerResponse>) {
                getNavigator()?.hideSpin()
                val responseBody = response.body()
                val statusCode = responseBody?.statuscode
                if (statusCode == 200) {
                    val players = responseBody.players
                    for (data in players) {
                        repository.savePlayers(data)
                    }
                    getNavigator()?.moveToScoutPage()

//                    val playerArray = ArrayList(players)
//                    Log.d("playersvm", playerArray.toString())
                   // getNavigator()?.displayPlayers(playerArray)
                } else {
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }

            override fun onFailure(call: Call<ScoutPlayerResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }
        })
    }


}

interface HomeNavigator {

    fun moveToScoutPage()

    //fun displayPlayers(players: ArrayList<Player>?)

    fun showSnackBarMessage(msg: String)

    fun scout()

    fun showSpin()

    fun hideSpin()
}

@Module
class HomeModule {

    @Provides
    fun provideHomeViewModel(application: Application): HomeViewModel {
        return HomeViewModel(application)
    }


}

