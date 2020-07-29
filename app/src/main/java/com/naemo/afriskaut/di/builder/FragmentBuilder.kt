package com.naemo.afriskaut.di.builder

import com.naemo.afriskaut.views.fragments.notifications.NotificationFragment
import com.naemo.afriskaut.views.fragments.notifications.NotificationModule
import com.naemo.afriskaut.views.fragments.player.pickstats.PickStatsFragment
import com.naemo.afriskaut.views.fragments.player.pickstats.PickStatsModule
import com.naemo.afriskaut.views.fragments.player.playerinfo.PlayerInfoFragment
import com.naemo.afriskaut.views.fragments.player.playerinfo.PlayerInfoModule
import com.naemo.afriskaut.views.fragments.player.playerinfo.PlayerInfoNavigator
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
}

@Module
abstract class PageFragmentBuilder {

    @ContributesAndroidInjector(modules = [PlayerInfoModule::class])
    abstract fun contributePlayerInfoFragment(): PlayerInfoFragment

    @ContributesAndroidInjector(modules = [PickStatsModule::class])
    abstract fun contributePickStatsFragment(): PickStatsFragment

    @ContributesAndroidInjector(modules = [StatsModule::class])
    abstract fun contributeStatsFragment(): StatsFragment
}