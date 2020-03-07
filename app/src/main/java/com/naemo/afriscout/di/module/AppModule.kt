package com.naemo.afriscout.di.module

import android.app.Application
import android.content.Context
import com.naemo.afriscout.db.local.preferences.AppPreferences
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.utils.AppUtils
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

    @Singleton
    @Provides
    fun provideClient(): Client {
        return Client()
    }

    @Singleton
    @Provides
    fun providesAppPreferences(context: Context): AppPreferences {
        return AppPreferences(context)
    }

    @Singleton
    @Provides
    fun providesAppUtils(): AppUtils {
        return AppUtils()
    }
}