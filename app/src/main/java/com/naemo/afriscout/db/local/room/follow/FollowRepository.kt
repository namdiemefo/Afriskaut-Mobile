package com.naemo.afriscout.db.local.room.follow

import android.app.Application
import android.content.Context
import android.util.Log
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.network.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FollowRepository(application: Application): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val TAG = "repository"
    var followPlayerDao: FollowPlayerDao?  = null

    init {
        val dataBase = FollowPlayerDataBase.invoke(application)
        followPlayerDao = dataBase.followplayerdao()
    }

    fun save(data: FolloData) {
        launch {
            savePlayer(data)
        }
    }

    private suspend fun savePlayer(data: FolloData) {
        Log.d(TAG, "SAVING THE FOLLOW")
        withContext(IO) {
            Log.d(TAG, "SAVed THE FOLLOW")
            followPlayerDao?.saveFollow(data)
        }
    }

    fun search(id: String): Boolean? {
        var check: Boolean? = null
        launch {
            Log.d(TAG, "CHECKING THE DB FOR FOLLOW")
            check = searchPlayer(id)
        }
        return check
    }

    private suspend fun searchPlayer(id: String): Boolean? {
        var check: Boolean? = null
        withContext(IO) {
            Log.d(TAG, "CHECKED THE DB FOR FOLLOW")
            check = followPlayerDao?.searchFollow(id)
        }
        return check
    }
}