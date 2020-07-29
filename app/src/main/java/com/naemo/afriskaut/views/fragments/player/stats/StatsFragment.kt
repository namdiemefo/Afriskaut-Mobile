package com.naemo.afriskaut.views.fragments.player.stats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.naemo.afriskaut.BR

import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.FragmentStatsBinding
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainerNavigator
import com.naemo.afriskaut.views.base.BaseFragment
import javax.inject.Inject

class StatsFragment : BaseFragment<FragmentStatsBinding, StatsViewModel>(), StatsNavigator {

    var statsViewModel: StatsViewModel? = null
        @Inject set

    private lateinit var fragmentNavigator: FragmentContainerNavigator;

    private var mLayoutId = R.layout.fragment_stats

    var mBinder: FragmentStatsBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigator = context as FragmentContainerNavigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = statsViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): StatsViewModel? {
        return statsViewModel
    }

    override fun goBack() {
        val args by navArgs<StatsFragmentArgs>()
        val player = args.player
        fragmentNavigator.navigateToPickStatsPage(player)
    }

}
