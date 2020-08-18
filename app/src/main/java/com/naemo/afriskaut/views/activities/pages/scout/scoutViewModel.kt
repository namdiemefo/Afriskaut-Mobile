package com.naemo.afriskaut.views.activities.pages.scout

import android.app.Application
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.R
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.scout.Player
import com.naemo.afriskaut.db.local.room.scout.ScoutRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class ScoutViewModel(application: Application): BaseViewModel<ScoutNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    private var repository: ScoutRepository = ScoutRepository(application)


    fun retrievePlayers() : LiveData<List<Player>>? {
        return repository.loadPlayers()
    }

}

interface ScoutNavigator

@Module
class ScoutModule {
    @Provides
    fun providesScoutViewModel(application: Application): ScoutViewModel {
        return ScoutViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_scout
    }
}