package com.naemo.afriskaut.di.builder

import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainer
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainerModule
import com.naemo.afriskaut.views.activities.account.forgotpassword.ForgotActivity
import com.naemo.afriskaut.views.activities.account.forgotpassword.ForgotModule
import com.naemo.afriskaut.views.activities.account.login.LoginActivity
import com.naemo.afriskaut.views.activities.account.login.LoginModule
import com.naemo.afriskaut.views.activities.account.register.RegisterActivity
import com.naemo.afriskaut.views.activities.account.register.RegisterModule
import com.naemo.afriskaut.views.activities.main.MainActivity
import com.naemo.afriskaut.views.activities.main.MainModule
import com.naemo.afriskaut.views.activities.pages.radar.RadarActivity
import com.naemo.afriskaut.views.activities.pages.radar.RadarModule
import com.naemo.afriskaut.views.activities.pages.report.create.CreateReportActivity
import com.naemo.afriskaut.views.activities.pages.report.create.CreateReportModule
import com.naemo.afriskaut.views.activities.pages.report.view.ViewMatchReportsActivity
import com.naemo.afriskaut.views.activities.pages.report.view.ViewMatchReportsModule
import com.naemo.afriskaut.views.activities.pages.scout.ScoutActivity
import com.naemo.afriskaut.views.activities.pages.scout.ScoutModule
import com.naemo.afriskaut.views.activities.pages.suggest.SuggestionActivity
import com.naemo.afriskaut.views.activities.pages.suggest.SuggestionModule
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

    @ContributesAndroidInjector(modules = [FragmentContainerModule::class, PageFragmentBuilder::class])
    abstract fun bindFragmentContainer(): FragmentContainer

    @ContributesAndroidInjector(modules = [RadarModule::class])
    abstract fun bindRadarActivity(): RadarActivity

    @ContributesAndroidInjector(modules = [ScoutModule::class])
    abstract fun bindScoutActivity(): ScoutActivity

    @ContributesAndroidInjector(modules = [SuggestionModule::class])
    abstract fun bindSuggestionActivity(): SuggestionActivity

    @ContributesAndroidInjector(modules = [ViewMatchReportsModule::class])
    abstract fun bindViewMatchReportsActivity(): ViewMatchReportsActivity

    @ContributesAndroidInjector(modules = [CreateReportModule::class])
    abstract fun bindCreateReportActivity(): CreateReportActivity

}