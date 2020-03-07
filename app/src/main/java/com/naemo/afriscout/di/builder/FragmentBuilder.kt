package com.naemo.afriscout.di.builder

import com.naemo.afriscout.views.notifications.NotificationFragment
import com.naemo.afriscout.views.notifications.NotificationModule
import com.naemo.afriscout.views.profile.ProfileFragment
import com.naemo.afriscout.views.profile.ProfileModule
import com.naemo.afriscout.views.search.SearchFragment
import com.naemo.afriscout.views.search.SearchModule
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