package com.naemo.afriskaut.db.local.room.search

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.network.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var searchDao: SearchDao? = null
    private val context: Context? = null
    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    init {
        val database = SearchDatabase.invoke(application)
        searchDao = database.searchDao()
    }

    fun loadSearchResults(): LiveData<List<Data>>? {
        return searchDao?.loadSearch()
    }

    fun updateFollowing(following: Boolean, id: Int) {
        launch {
            updateField(following, id)
        }
    }

    private suspend fun updateField(following: Boolean, id: Int) {
        withContext(IO) {
            searchDao?.update(following, id)
        }
    }

    fun saveTheResult(data: Data?) {
        launch {
            save(data)
        }
    }

    private suspend fun save(data: Data?) {
        withContext(IO) {
            searchDao?.deleteSearch()
            searchDao?.saveSearch(data!!)
        }
    }

    fun loadPlayerData(playerId: Int): LiveData<Data>? {
        return searchDao?.loadPlayer(playerId)
    }

}