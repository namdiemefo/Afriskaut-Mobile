package com.naemo.afriskaut.db.local.room.follow

import android.app.Application
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
        withContext(IO) {
            followPlayerDao?.saveFollow(data)
        }
    }

    fun search(id: String): Boolean? {
        var check: Boolean? = null
        launch {
            check = searchPlayer(id)
        }
        return check
    }

    private suspend fun searchPlayer(id: String): Boolean? {
        var check: Boolean? = null
        withContext(IO) {
            check = followPlayerDao?.searchFollow(id)
        }
        return check
    }
}