package com.naemo.afriscout

import android.app.Activity
import android.app.Application
import com.naemo.afriscout.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class AfriScout : Application(), HasActivityInjector {

    internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null
    @Inject set

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }
}