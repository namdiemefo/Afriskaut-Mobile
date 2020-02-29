package com.naemo.afriscout.di.builder


import com.naemo.afriscout.views.account.login.LoginActivity
import com.naemo.afriscout.views.account.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

}