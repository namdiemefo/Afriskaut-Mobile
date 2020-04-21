package com.naemo.afriscout.views.fragments.search

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriscout.api.models.search.SearchRequest
import com.naemo.afriscout.api.models.search.SearchResponse
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.db.local.room.search.Data
import com.naemo.afriscout.db.local.room.search.SearchRepository
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.views.base.BaseViewModel
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

    fun retrieveSearchResults(): LiveData<List<Data>>? {
        Log.d(TAG, "GETTING THE SEARCH RESULTS")
        return repository?.loadSearchResults()
    }

    fun save() {
        getNavigator()?.showSpin()
        val query = search.get().toString()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val search = SearchRequest(query)
        Log.d(TAG, "ABOUT TO MAKE SEARCH CALL")
        val searchCall: Call<SearchResponse> = client.getApi().search(token, search)
        searchCall.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                getNavigator()?.hideSpin()
                val searchResponse: SearchResponse? = response.body()
                val statusCode = searchResponse?.statuscode
                if (statusCode == 200) {
                        val data = response.body()?.data
                        saveResult(data)
                    getNavigator()?.retrieveFromDb()
                } else {
                    getNavigator()?.retrieveFromDb()
                    getNavigator()?.showSnackBarMessage("player not found")
                }

            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                getNavigator()?.retrieveFromDb()
                Log.d(TAG, "SEARCH CALL FAILED")
                getNavigator()?.showSnackBarMessage("server error")
            }
        })
    }

    private fun saveResult(data: Data?) {
        repository?.saveTheResult(data)
    }
}

interface SearchNavigator {
    fun sendSearch()

    fun showSnackBarMessage(msg: String)

    fun showSpin()

    fun hideSpin()

    fun retrieveFromDb()
}

@Module
class SearchModule {

    @Provides
    fun providesSearchViewModel(application: Application): SearchViewModel {
        return SearchViewModel(application)
    }
}
