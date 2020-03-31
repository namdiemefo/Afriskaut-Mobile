package com.naemo.afriscout.views.activities.pages.playerstats.statspage

import android.app.Application
import com.naemo.afriscout.R
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class StatsPageViewModel(application: Application): BaseViewModel<StatsPageNavigator>(application) {

}
interface StatsPageNavigator {

    fun goBack()

    fun pickClub()

    fun goToAllTime()
}

@Module
class StatsPageModule {
    @Provides
    fun providesStatsPageViewModel(application: Application): StatsPageViewModel {
        return StatsPageViewModel(
            application
        )
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_stats_page
    }
}