package com.naemo.afriscout.di.builder

import com.naemo.afriscout.views.fragments.notifications.NotificationFragment
import com.naemo.afriscout.views.fragments.notifications.NotificationModule
import com.naemo.afriscout.views.fragments.profile.ProfileFragment
import com.naemo.afriscout.views.fragments.profile.ProfileModule
import com.naemo.afriscout.views.fragments.search.SearchFragment
import com.naemo.afriscout.views.fragments.search.SearchModule
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