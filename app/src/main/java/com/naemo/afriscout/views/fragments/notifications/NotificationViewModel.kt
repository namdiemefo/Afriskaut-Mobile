package com.naemo.afriscout.views.fragments.notifications

import android.app.Application
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class NotificationViewModel(application: Application) : BaseViewModel<NotificationNavigator>(application) {

}

interface NotificationNavigator {

}

@Module
class NotificationModule {

    @Provides
    fun providesNotificationViewModel(application: Application): NotificationViewModel {
        return NotificationViewModel(application)
    }
}
