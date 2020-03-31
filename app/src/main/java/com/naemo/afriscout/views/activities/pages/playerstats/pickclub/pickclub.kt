package com.naemo.afriscout.views.activities.pages.playerstats.pickclub

import android.app.Application
import com.naemo.afriscout.R
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class PickClubViewModel(application: Application): BaseViewModel<PickClubNavigator>(application) {

}

interface PickClubNavigator {

    fun goBack()
}

@Module
class PickClubModule {

    @Provides
    fun providesPickClubViewModel(application: Application): PickClubViewModel {
        return PickClubViewModel(
            application
        )
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_pick_club_page
    }
}