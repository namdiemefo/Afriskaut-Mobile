package com.naemo.afriscout.di.builder


import com.naemo.afriscout.views.activities.account.forgotpassword.ForgotActivity
import com.naemo.afriscout.views.activities.account.forgotpassword.ForgotModule
import com.naemo.afriscout.views.activities.account.login.LoginActivity
import com.naemo.afriscout.views.activities.account.login.LoginModule
import com.naemo.afriscout.views.activities.account.register.RegisterActivity
import com.naemo.afriscout.views.activities.account.register.RegisterModule
import com.naemo.afriscout.views.activities.main.MainActivity
import com.naemo.afriscout.views.activities.main.MainModule
import com.naemo.afriscout.views.fragments.profile.ProfileFragment
import com.naemo.afriscout.views.fragments.profile.ProfileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [RegisterModule::class])
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [ForgotModule::class])
    abstract fun bindForgotActivity(): ForgotActivity

    @ContributesAndroidInjector(modules = [MainModule::class, FragmentBuilder::class])
    abstract fun bindMainActivity(): MainActivity

}