package com.naemo.afriskaut.views.activities.pages.playerstats

import android.app.Application
import com.naemo.afriskaut.R
import com.naemo.afriskaut.db.local.room.search.Player
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides


class FragmentContainerViewModel(application: Application): BaseViewModel<FragmentContainerNavigator>(application)

interface FragmentContainerNavigator {

    fun navigateToStatsPage(player: Player)

    fun navigateToPickStatsPage(player: Player)

    fun fromInfoToPickStats(player: Player)

    fun fromPickStatsToInfo(player: Player)

    fun moveBack()

}

@Module
class FragmentContainerModule {

    @Provides
    fun fragmentContainerViewModel(application: Application): FragmentContainerViewModel {
        return FragmentContainerViewModel(
            application
        )
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_fragment_container
    }
}