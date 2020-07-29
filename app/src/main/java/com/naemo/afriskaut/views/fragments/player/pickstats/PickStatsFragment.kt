package com.naemo.afriskaut.views.fragments.player.pickstats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.naemo.afriskaut.BR

import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.FragmentPickStatsBinding
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainerNavigator
import com.naemo.afriskaut.views.base.BaseFragment
import javax.inject.Inject

class PickStatsFragment : BaseFragment<FragmentPickStatsBinding, PickStatsViewModel>(), PickStatsNavigator {

    private lateinit var fragmentNavigator: FragmentContainerNavigator

    var mBinder: FragmentPickStatsBinding? = null

    val mLayoutId = R.layout.fragment_pick_stats

    var pickStatsViewModel: PickStatsViewModel? = null
        @Inject set

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
        mBinder?.viewModel = pickStatsViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): PickStatsViewModel? {
        return pickStatsViewModel
    }

    override fun goBack() {
        val args by navArgs<PickStatsFragmentArgs>()
        val player = args.player
        fragmentNavigator.fromPickStatsToInfo(player)
    }

    override fun goToTeamStats() {
        val args by navArgs<PickStatsFragmentArgs>()
        val player = args.player
        fragmentNavigator.navigateToStatsPage(player)
    }

    override fun goToTournamentStats() {
        val args by navArgs<PickStatsFragmentArgs>()
        val player = args.player
        fragmentNavigator.navigateToStatsPage(player)
    }

    override fun goToSeasonStats() {
        val args by navArgs<PickStatsFragmentArgs>()
        val player = args.player
        fragmentNavigator.navigateToStatsPage(player)
    }

    override fun goToAllTimeStats() {
        val args by navArgs<PickStatsFragmentArgs>()
        val player = args.player
        fragmentNavigator.navigateToStatsPage(player)

    }

}
