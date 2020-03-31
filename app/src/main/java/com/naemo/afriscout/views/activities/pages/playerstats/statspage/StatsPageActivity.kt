package com.naemo.afriscout.views.activities.pages.playerstats.statspage

import android.content.Intent
import android.os.Bundle
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityStatsPageBinding
import com.naemo.afriscout.views.activities.pages.playerstats.allstats.AllStatsActivity
import com.naemo.afriscout.views.activities.pages.playerstats.pickclub.PickClubActivity
import com.naemo.afriscout.views.base.BaseActivity
import javax.inject.Inject

class StatsPageActivity : BaseActivity<ActivityStatsPageBinding, StatsPageViewModel>(),
    StatsPageNavigator {

    var statsPageViewModel: StatsPageViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_stats_page
        @Inject set

    var mBinder: ActivityStatsPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = statsPageViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun pickClub() {
        startActivity(Intent(this, PickClubActivity::class.java))
    }

    override fun goToAllTime() {
        startActivity(Intent(this, AllStatsActivity::class.java))
    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): StatsPageViewModel? {
        return statsPageViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }


}
