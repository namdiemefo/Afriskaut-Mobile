package com.naemo.afriskaut.db.local.room.following

import android.app.Application
import android.util.Log
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

    private var followingPlayerDao: FollowingPlayerDao? = null

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
            delete()
            followingPlayerDao?.saveFollow(data)
        }
    }

    fun delete() {
        launch { deleteFollowing() }
    }

    private suspend fun deleteFollowing() {
        withContext(IO) {
            Log.d("delete", "deleted")
            followingPlayerDao?.deleteRadar()
        }
    }

    fun search(name: String): LiveData<List<FollowingData>>? {
        return followingPlayerDao?.searchFor(name)
    }



}