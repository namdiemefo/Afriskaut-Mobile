package com.naemo.afriscout.db.local.room.following

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FollowingRepository(application: Application): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val TAG = "Followingrepository"
    var followingPlayerDao: FollowingPlayerDao?  = null

    init {
        val dataBase = FollowingPlayerDataBase.invoke(application)
        followingPlayerDao = dataBase.followingplayerdao()
    }

    fun save(data: FollowingData) {
        launch {
            saveFollowing(data)
        }
    }

    private suspend fun saveFollowing(data: FollowingData) {
        Log.d(TAG, "SAVING THE FOLLOWING ")
        withContext(Dispatchers.IO) {
            Log.d(TAG, "SAVED THE FOLLOWING")
            followingPlayerDao?.saveFollow(data)
        }
    }

    fun search(id: String): Boolean? {
        var check: Boolean? = null
        launch {
            Log.d(TAG, "CHECKING THE DB FOR FOLLOWING")
            check = searchPlayer(id)
        }
        return check
    }

    private suspend fun searchPlayer(id: String): Boolean? {
        var check: Boolean? = null
        withContext(Dispatchers.IO) {
            Log.d(TAG, id)
            Log.d(TAG, "CHECKED THE DB FOR FOLLOWING")
            check = followingPlayerDao?.searchFollowing(id)
        }
        return check
    }
}