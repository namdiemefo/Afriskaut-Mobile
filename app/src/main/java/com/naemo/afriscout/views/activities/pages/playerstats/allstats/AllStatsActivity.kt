package com.naemo.afriscout.views.activities.pages.playerstats.allstats

import android.os.Bundle
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityAllStatsBinding
import com.naemo.afriscout.views.base.BaseActivity
import javax.inject.Inject

class AllStatsActivity : BaseActivity<ActivityAllStatsBinding, AllStatsViewModel>(), AllStatsNavigator {

    var allStatsViewModel: AllStatsViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_all_stats
        @Inject set

    var mBinder: ActivityAllStatsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = allStatsViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): AllStatsViewModel? {
        return allStatsViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun goBack() {
        onBackPressed()
    }
}
