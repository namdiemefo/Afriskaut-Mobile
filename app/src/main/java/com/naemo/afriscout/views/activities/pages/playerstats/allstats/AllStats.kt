package com.naemo.afriscout.views.activities.pages.playerstats.allstats

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriscout.R
import com.naemo.afriscout.db.local.room.search.Data
import com.naemo.afriscout.db.local.room.search.SearchRepository
import com.naemo.afriscout.db.local.room.stats.PlayerStats
import com.naemo.afriscout.db.local.room.stats.Stats
import com.naemo.afriscout.db.local.room.stats.StatsRepository
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import java.text.FieldPosition

class AllStatsViewModel(application: Application): BaseViewModel<AllStatsNavigator>(application) {

    var image = ObservableField<String>()
    var age = ObservableField("")
    var nation = ObservableField("")
    var position = ObservableField("")

    private var repository: StatsRepository? = null
    private var searchRepository: SearchRepository? = null

    init {
        repository = StatsRepository(application)
        searchRepository = SearchRepository(application)
    }

    fun getPlayerBio(playerId: Int): LiveData<Data>? {
        return searchRepository?.loadPlayerData(playerId)
    }

    fun setBio(img: String?, playerAge: String?, nationality: String?, playerPosition: String?) {
        image.set(img)
        age.set(playerAge)
        nation.set(nationality)
        position.set(playerPosition)
    }

    fun getStats(id: Int): LiveData<Stats>? {
        return repository?.loadOne(id)
    }

}

interface AllStatsNavigator {

    fun goBack()
}

@Module
class AllStatsModule {
    @Provides
    fun provideAllStatsViewModel(application: Application): AllStatsViewModel {
        return AllStatsViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_all_stats
    }
}