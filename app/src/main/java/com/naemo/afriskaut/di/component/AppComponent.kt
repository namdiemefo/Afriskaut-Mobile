package com.naemo.afriskaut.di.component

import android.app.Application
import com.naemo.afriskaut.Afriskaut
import com.naemo.afriskaut.di.builder.ActivityBuilder
import com.naemo.afriskaut.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {

    fun inject(afriskaut: Afriskaut)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}