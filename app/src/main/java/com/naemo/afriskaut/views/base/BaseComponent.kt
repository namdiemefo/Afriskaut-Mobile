package com.naemo.afriskaut.views.base

import com.naemo.afriskaut.utils.NetworkUtils
import dagger.Component

@Component
interface BaseComponent {

    fun getNetwork(): NetworkUtils
}