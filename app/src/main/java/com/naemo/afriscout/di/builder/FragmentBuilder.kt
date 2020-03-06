package com.naemo.afriscout.di.builder

import com.naemo.afriscout.views.profile.ProfileFragment
import com.naemo.afriscout.views.profile.ProfileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun bindProfileFragment(): ProfileFragment
}