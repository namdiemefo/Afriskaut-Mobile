package com.naemo.afriskaut.views.fragments.player.pickstats

import android.app.Application
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class PickStatsViewModel(application: Application) : BaseViewModel<PickStatsNavigator>(application)

interface PickStatsNavigator {

    fun goBack()

    fun goToTeamStats()

    fun goToTournamentStats()

    fun goToSeasonStats()

    fun goToAllTimeStats()

}

@Module
class PickStatsModule {

    @Provides
    fun providesPickStatsViewModel(application: Application): PickStatsViewModel {
        return PickStatsViewModel(application)
    }
}