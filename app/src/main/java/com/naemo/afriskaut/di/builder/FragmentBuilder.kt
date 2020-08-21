package com.naemo.afriskaut.di.builder

import com.naemo.afriskaut.views.fragments.home.HomeFragment
import com.naemo.afriskaut.views.fragments.home.HomeModule
import com.naemo.afriskaut.views.fragments.notifications.NotificationFragment
import com.naemo.afriskaut.views.fragments.notifications.NotificationModule
import com.naemo.afriskaut.views.fragments.player.decidestats.DecideStats
import com.naemo.afriskaut.views.fragments.player.decidestats.DecideStatsModule
import com.naemo.afriskaut.views.fragments.player.playerinfo.PlayerInfoFragment
import com.naemo.afriskaut.views.fragments.player.playerinfo.PlayerInfoModule
import com.naemo.afriskaut.views.fragments.player.stats.StatsFragment
import com.naemo.afriskaut.views.fragments.player.stats.StatsModule
import com.naemo.afriskaut.views.fragments.profile.ProfileFragment
import com.naemo.afriskaut.views.fragments.profile.ProfileModule
import com.naemo.afriskaut.views.fragments.search.SearchFragment
import com.naemo.afriskaut.views.fragments.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun contributeNotificationFragment(): NotificationFragment

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contribueSearchFragment(): SearchFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment
}

@Module
abstract class PageFragmentBuilder {

    @ContributesAndroidInjector(modules = [PlayerInfoModule::class])
    abstract fun contributePlayerInfoFragment(): PlayerInfoFragment

    @ContributesAndroidInjector(modules = [StatsModule::class])
    abstract fun contributeStatsFragment(): StatsFragment

    @ContributesAndroidInjector(modules = [DecideStatsModule::class])
    abstract fun contributeDecideStatsFragment(): DecideStats
}