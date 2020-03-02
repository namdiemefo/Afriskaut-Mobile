package com.naemo.afriscout.views.main

import android.os.Bundle
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityMainBinding
import com.naemo.afriscout.views.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator{

    var mainViewModel: MainViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_main
        @Inject set

    var mBinder: ActivityMainBinding? = null
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainViewModel? {
        return mainViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = mainViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }
}
