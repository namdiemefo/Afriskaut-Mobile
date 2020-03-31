package com.naemo.afriscout.views.activities.pages.playerstats.allstats

import android.app.Application
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class AllStatsViewModel(application: Application): BaseViewModel<AllStatsNavigator>(application) {

    var image = ObservableField<String>()
    var age = ObservableField("")
    var nation = ObservableField("")
    var position = ObservableField("")

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