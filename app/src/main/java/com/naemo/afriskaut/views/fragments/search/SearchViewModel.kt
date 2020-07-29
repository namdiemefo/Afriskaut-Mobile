package com.naemo.afriskaut.views.fragments.search

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.api.models.search.SearchPlayerRequest
import com.naemo.afriskaut.api.models.search.SearchPlayerResponse
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.search.Player
import com.naemo.afriskaut.db.local.room.search.SearchRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel(application: Application) : BaseViewModel<SearchNavigator>(application) {

    var search = ObservableField("")
    private var repository: SearchRepository? = null
    val TAG = "SearchViewModel"

    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    init {
        repository = SearchRepository(application)
    }

    fun retrieveSearchResults(): LiveData<List<Player>>? {
        return repository?.loadSearchResults()
    }

    fun save() {
        getNavigator()?.showSpin()
        val field = search.get().toString()
        val keyword = search.get().toString()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val search = SearchPlayerRequest(field, keyword)
        val searchCall: Call<SearchPlayerResponse> = client.getApi().search(token, search)
        searchCall.enqueue(object : Callback<SearchPlayerResponse> {
            override fun onResponse(call: Call<SearchPlayerResponse>, response: Response<SearchPlayerResponse>) {
                getNavigator()?.hideSpin()
                val searchResponse: SearchPlayerResponse? = response.body()
                val statusCode = searchResponse?.statuscode
                if (statusCode == 200) {
                        val data = response.body()?.players
                    if (data != null) {
                        for (player in data) {
                            saveResult(player)
                        }
                    }
                    data?.let { getNavigator()?.load(it) }
                    //getNavigator()?.retrieveFromDb()
                } else {
                    getNavigator()?.retrieveFromDb()
                    getNavigator()?.showSnackBarMessage("player not found")
                }

            }

            override fun onFailure(call: Call<SearchPlayerResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                getNavigator()?.retrieveFromDb()
                getNavigator()?.showSnackBarMessage("server error")
            }
        })
    }

    private fun saveResult(data: Player?) {
        repository?.saveTheResult(data)
    }
}

interface SearchNavigator {
    fun sendSearch()

    fun showSnackBarMessage(msg: String)

    fun showSpin()

    fun hideSpin()

    fun load(player: List<Player>)

    fun retrieveFromDb()
}

@Module
class SearchModule {

    @Provides
    fun providesSearchViewModel(application: Application): SearchViewModel {
        return SearchViewModel(application)
    }
}
