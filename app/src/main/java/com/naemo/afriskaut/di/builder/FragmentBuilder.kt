package com.naemo.afriskaut.di.builder

import com.naemo.afriskaut.views.fragments.notifications.NotificationFragment
import com.naemo.afriskaut.views.fragments.notifications.NotificationModule
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