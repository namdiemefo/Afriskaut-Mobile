package com.naemo.afriskaut.views.fragments.player.decidestats

import android.app.Application
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class DecideStatsViewModel(application: Application) : BaseViewModel<DecideStatsNavigator>(application)

interface DecideStatsNavigator {

    fun goBack()
}

@Module
class DecideStatsModule {

    @Provides
    fun providesDecideStatsViewModel(application: Application): DecideStatsViewModel {
        return DecideStatsViewModel(application)
    }
}
