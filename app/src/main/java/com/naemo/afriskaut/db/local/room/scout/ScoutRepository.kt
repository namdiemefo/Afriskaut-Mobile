package com.naemo.afriskaut.db.local.room.scout

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.network.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ScoutRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var scoutDao: ScoutDao? = null
    private val context: Context? = null
    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    init {
        val database = ScoutDatabase.invoke(application)
        scoutDao = database.scoutDao()
    }

    fun loadPlayers(): LiveData<List<Player>>? {
        return scoutDao?.loadSearch()
    }


    fun savePlayers(data: Player?) {
        launch {
            save(data)
        }
    }

    private suspend fun save(data: Player?) {
        withContext(Dispatchers.IO) {
            scoutDao?.deleteSearch()
            scoutDao?.saveSearch(data!!)
        }
    }
}