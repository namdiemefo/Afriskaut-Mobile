package com.naemo.afriscout.di.builder


import com.naemo.afriscout.views.activities.account.forgotpassword.ForgotActivity
import com.naemo.afriscout.views.activities.account.forgotpassword.ForgotModule
import com.naemo.afriscout.views.activities.account.login.LoginActivity
import com.naemo.afriscout.views.activities.account.login.LoginModule
import com.naemo.afriscout.views.activities.account.register.RegisterActivity
import com.naemo.afriscout.views.activities.account.register.RegisterModule
import com.naemo.afriscout.views.activities.main.MainActivity
import com.naemo.afriscout.views.activities.main.MainModule
import com.naemo.afriscout.views.activities.pages.playerprofile.PlayerProfileActivity
import com.naemo.afriscout.views.activities.pages.playerprofile.PlayerProfileModule
import com.naemo.afriscout.views.activities.pages.playerstats.allstats.AllStatsActivity
import com.naemo.afriscout.views.activities.pages.playerstats.allstats.AllStatsModule
import com.naemo.afriscout.views.activities.pages.playerstats.pickclub.PickClubActivity
import com.naemo.afriscout.views.activities.pages.playerstats.pickclub.PickClubModule
import com.naemo.afriscout.views.activities.pages.playerstats.statspage.StatsPageActivity
import com.naemo.afriscout.views.activities.pages.playerstats.statspage.StatsPageModule
import com.naemo.afriscout.views.activities.pages.radar.RadarActivity
import com.naemo.afriscout.views.activities.pages.radar.RadarModule
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

    @ContributesAndroidInjector(modules = [PlayerProfileModule::class])
    abstract fun bindPlayerProfileActivity(): PlayerProfileActivity

    @ContributesAndroidInjector(modules = [StatsPageModule::class])
    abstract fun bindStatsPageActivity(): StatsPageActivity

    @ContributesAndroidInjector(modules = [PickClubModule::class])
    abstract fun bindPickClubActivity(): PickClubActivity

    @ContributesAndroidInjector(modules = [AllStatsModule::class])
    abstract fun bindAllStatsActivity(): AllStatsActivity

    @ContributesAndroidInjector(modules = [RadarModule::class])
    abstract fun bindRadarActivity(): RadarActivity

}