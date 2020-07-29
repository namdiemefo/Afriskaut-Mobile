package com.naemo.afriskaut.views.fragments.player.stats

import android.app.Application
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class StatsViewModel(application: Application) : BaseViewModel<StatsNavigator>(application)

interface StatsNavigator {

    fun goBack()

}

@Module
class StatsModule {

    @Provides
    fun providesStatsViewModel(application: Application): StatsViewModel {
        return StatsViewModel(application)
    }
}