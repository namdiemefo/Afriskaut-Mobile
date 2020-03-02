package com.naemo.afriscout.views.main

import android.app.Application
import com.naemo.afriscout.R
import com.naemo.afriscout.views.account.login.LoginViewModel
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class MainViewModel(application: Application) : BaseViewModel<MainNavigator>(application) {

}

interface MainNavigator {

}

@Module
class MainModule {

    @Provides
    fun providesMainViewModel(application: Application): MainViewModel {
        return MainViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_main
    }
}