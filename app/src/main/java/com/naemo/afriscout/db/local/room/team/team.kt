package com.naemo.afriscout.db.local.room.team

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


data class Team(

    var teamName: Map<Int, String>?,
    var flag: Map<String, String>?
)



