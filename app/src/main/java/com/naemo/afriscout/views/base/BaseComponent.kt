package com.naemo.afriscout.views.base

import com.naemo.afriscout.utils.NetworkUtils
import dagger.Component

@Component
interface BaseComponent {

    fun getNetwork(): NetworkUtils
}