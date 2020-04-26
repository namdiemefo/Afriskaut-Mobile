package com.naemo.afriscout.db.local.room.following

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FollowingRepository(application: Application): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val TAG = "Followingrepository"
    var followingPlayerDao: FollowingPlayerDao? = null

    init {
        val dataBase = FollowingPlayerDataBase.invoke(application)
        followingPlayerDao = dataBase.followingplayerdao()
    }

    fun loadFollowing(): LiveData<List<FollowingData>>? {
        return followingPlayerDao?.loadFollowing()
    }

    fun save(data: FollowingData) {
        launch {
            saveFollowing(data)
        }
    }

    private suspend fun saveFollowing(data: FollowingData) {
        withContext(IO) {
            followingPlayerDao?.deleteRadar()
            followingPlayerDao?.saveFollow(data)
        }
    }

    fun search(name: String): LiveData<List<FollowingData>>? {
        return followingPlayerDao?.searchFor(name)
    }



}