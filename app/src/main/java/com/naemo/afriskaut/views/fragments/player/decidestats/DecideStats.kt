package com.naemo.afriskaut.views.fragments.player.decidestats

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.search.Stats
import com.naemo.afriskaut.databinding.DecideStatsFragmentBinding
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainerNavigator
import com.naemo.afriskaut.views.adapters.ClubAdapter
import com.naemo.afriskaut.views.adapters.NationAdapter
import com.naemo.afriskaut.views.base.BaseFragment
import kotlinx.android.synthetic.main.decide_stats_fragment.*
import javax.inject.Inject


class DecideStats : BaseFragment<DecideStatsFragmentBinding, DecideStatsViewModel>(), DecideStatsNavigator, ClubAdapter.ItemClickListener,
NationAdapter.ItemClickListener {

    var decideStatsViewModel: DecideStatsViewModel? = null
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    private lateinit var fragmentNavigator: FragmentContainerNavigator

    private var mLayoutId = R.layout.decide_stats_fragment

    var mBinder: DecideStatsFragmentBinding? = null


    private lateinit var viewModel: DecideStatsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigator = context as FragmentContainerNavigator
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doBinding()
        initViews()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViews() {
        val args by navArgs<DecideStatsArgs>()
        val player = args.player
        val stats = player.stats
        retrieveDecisionName(stats)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun retrieveDecisionName(stats: List<Stats>?) {
        val teams = arrayListOf<String>()
        stats?.let {
            for (stat in it) {
                val team = stat.teamName
                team?.let { it1 -> teams.add(it1) }
            }
        }
        val team = teams.toSet().toList() as ArrayList
        val clubs = appUtils.checkIfsClub(team)
        val nations = appUtils.checkIfsCountry(team)

        val clubAdapter = ClubAdapter(requireContext(), clubs, this)
        val nationAdapter = NationAdapter(requireContext(), nations, this)

        nation_recycler_view.adapter = nationAdapter
        nation_recycler_view.layoutManager = LinearLayoutManager(requireContext())

        club_recycler_view.adapter = clubAdapter
        club_recycler_view.layoutManager = LinearLayoutManager(requireContext())




    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = decideStatsViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): DecideStatsViewModel? {
        return decideStatsViewModel
    }

    override fun goBack() {
        val args by navArgs<DecideStatsArgs>()
        val player = args.player
        fragmentNavigator.navigateToInfoPageFromDecidesPage(player)
    }

    override fun onClubItemClicked(name: String) {
        val args by navArgs<DecideStatsArgs>()
        val player = args.player
        val stats = player.stats
        val single = arrayListOf<Stats>()
        stats?.let {
            for (stat in it) {
                val k = stat.teamName.toString()
                if (k == name) {
                    single.add(stat)
                }
            }
        }

        val clubStats = single.toTypedArray()
        fragmentNavigator.navigateToStatsPageFromDecidesPage(clubStats)
    }

    override fun onNationItemClicked(name: String) {
        val args by navArgs<DecideStatsArgs>()
        val player = args.player
        val stats = player.stats
        val single = arrayListOf<Stats>()
        stats?.let {
            for (stat in it) {
                val k = stat.teamName.toString()
                if (k == name) {
                    single.add(stat)
                }
            }
        }

        val nationStats = single.toTypedArray()
        fragmentNavigator.navigateToStatsPageFromDecidesPage(nationStats)


    }

}
