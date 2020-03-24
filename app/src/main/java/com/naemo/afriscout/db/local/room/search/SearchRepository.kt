package com.naemo.afriscout.db.local.room.search

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.naemo.afriscout.api.models.search.SearchRequest
import com.naemo.afriscout.api.models.search.SearchResponse
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.views.fragments.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val TAG = "SearchRepository"
    var searchDao: SearchDao? = null
    val context: Context? = null
    val searchViewModel: SearchViewModel? = null
    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    init {

        val database = SearchDatabase.invoke(application)
        searchDao = database.searchDao()
    }

    fun loadSearchResults(): LiveData<Data>? {
        Log.d(TAG, "RETURNING THE SEARCH RESULT")
        return searchDao?.loadSearch()
    }

    fun saveSearchResults(query: String) {
        Log.d(TAG, query)
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val search = SearchRequest(query)
        Log.d(TAG, "ABOUT TO MAKE SEARCH CALL")
        val searchCall: Call<SearchResponse> = client.getApi().search(token, search)
        searchCall.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val searchResponse: SearchResponse? = response.body()
                val statusCode = searchResponse?.statuscode
                if (statusCode == 200) {
                    Log.d(TAG, "SEARCH CALL SUCCESSFUL")
                    launch {
                        Log.d(TAG, "INSERTING SEARCH RESULTS")
                        val data = response.body()?.data
                        save(data)
                    }
                } else {
                    searchViewModel?.getNavigator()?.showSnackBarMessage("player not found")
                }

            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d(TAG, "SEARCH CALL FAILED")
                searchViewModel?.getNavigator()?.showSnackBarMessage("server error")
            }
        })
    }

    private suspend fun save(data: Data?) {
        withContext(IO) {
            Log.d(TAG, "SEARCH RESULTS INSERTED")
            searchDao?.deleteSearch()
            searchDao?.saveSearch(data!!)
        }
    }

}