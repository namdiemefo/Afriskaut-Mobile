package com.naemo.afriscout.di.component

import android.app.Application
import com.naemo.afriscout.AfriScout
import com.naemo.afriscout.di.builder.ActivityBuilder
import com.naemo.afriscout.di.builder.FragmentBuilder
import com.naemo.afriscout.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {

    fun inject(afriscout: AfriScout)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}