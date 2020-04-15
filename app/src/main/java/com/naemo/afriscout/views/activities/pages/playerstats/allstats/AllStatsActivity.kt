package com.naemo.afriscout.views.activities.pages.playerstats.allstats

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityAllStatsBinding
import com.naemo.afriscout.db.local.room.search.Data
import com.naemo.afriscout.db.local.room.stats.PlayerStats
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.adapters.StatsAdapter
import com.naemo.afriscout.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_all_stats.*
import javax.inject.Inject

class AllStatsActivity : BaseActivity<ActivityAllStatsBinding, AllStatsViewModel>(), AllStatsNavigator {

    var allStatsViewModel: AllStatsViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_all_stats
        @Inject set

    var context: Context? = null
        @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    init {
        this.context = context
    }

    var mBinder: ActivityAllStatsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
        initViews()
    }

    private fun initViews() {
        val intent = intent
        val id = intent.getIntExtra("stats", 0)

        var data = getViewModel()?.getPlayerBio()
        data?.observe(this, Observer {
            setUpBio(it)
        })
        val stats = getViewModel()?.getStats(id)
        stats?.observe(this, Observer {
            setUpStats(it)
        })
    }

    private fun setUpBio(it: Data?) {
        val img = it?.image
        val position = it?.position
        val nation = it?.nationality
        val dob = it?.dob
        val playerAge = appUtils?.getAge(dob)
        val age = playerAge?.toString()
        getViewModel()?.setBio(img, age, nation, position)

    }


    private fun setUpStats(it: PlayerStats?) {
        val adapter = it?.let { it1 -> StatsAdapter(this, it1) }
        stats_recycler_view.adapter = adapter
        stats_recycler_view.layoutManager = LinearLayoutManager(this)
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
