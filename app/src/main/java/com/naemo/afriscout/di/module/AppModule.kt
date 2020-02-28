package com.naemo.afriscout.di.module

import android.app.Application
import android.content.Context
import com.naemo.afriscout.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


}