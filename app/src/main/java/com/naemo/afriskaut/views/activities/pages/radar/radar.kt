package com.naemo.afriskaut.views.activities.pages.radar

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.player.follow.FollowingResponse
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.following.FollowingData
import com.naemo.afriskaut.db.local.room.following.FollowingRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RadarViewModel(application: Application): BaseViewModel<RadarNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    private var repository: FollowingRepository = FollowingRepository(application)

    val search = ObservableField("")

    fun makeSearch(): LiveData<List<FollowingData>>? {
       val query = search.get().toString()
        return repository.search(query)
    }

    fun retrieveRadar() : LiveData<List<FollowingData>>? {
        return repository.loadFollowing()
    }

    fun makeFollowersCall() {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"

        val radarCall: Call<FollowingResponse> = client.getApi().following(token)
        radarCall.enqueue(object : Callback<FollowingResponse> {
            override fun onResponse(call: Call<FollowingResponse>, response: Response<FollowingResponse>) {
                getNavigator()?.hideSpin()
                val radarResponse = response.body()
                val data = radarResponse?.data
                val statusCode = radarResponse?.statuscode
                if (statusCode == 200) {
                    data?.let {
                        for (i in it) {
                            repository.save(i)
                        }
                    }
                    data?.let { getNavigator()?.load(it) }
                } else {
                    getNavigator()?.loadFromDb()
                }
            }

            override fun onFailure(call: Call<FollowingResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                getNavigator()?.loadFromDb()
                getNavigator()?.showSnackBarMessage("server error")
            }
        })
    }
}

interface RadarNavigator {

    fun sendSearch()

    fun showSpin()

    fun hideSpin()

    fun showSnackBarMessage(msg: String)

    fun loadFromDb()

    fun load(data: List<FollowingData>)
}

@Module
class RadarModule {
    @Provides
    fun providesRadarViewModel(application: Application): RadarViewModel {
        return RadarViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_radar
    }
}