package com.naemo.afriskaut.views.activities.pages.playerstats.allstats

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityAllStatsBinding
import com.naemo.afriskaut.db.local.room.search.Data
import com.naemo.afriskaut.db.local.room.stats.PlayerStats
import com.naemo.afriskaut.db.local.room.stats.Stats
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseActivity
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
        val allStats = intent.getIntExtra("allStats", 0)
        val playerId = intent.getIntExtra("playerId", 0)
        val clickedId = intent.getIntExtra("clickedId", 0)

        val data = getViewModel()?.getPlayerBio(playerId)
        data?.observe(this, Observer {
            setUpBio(it)
        })

        Log.d("apss", allStats.toString())
        when (allStats) {
            1 -> {
                displayTeamStats(playerId, clickedId)
            }
            2 -> {
                displayTournamentStats(playerId, clickedId)
            }
            3 -> {
                displaySeasonStats(playerId, clickedId)
            }
            4 -> {
                displayAllTimeStats(playerId)
            }
        }


    }

    private fun displayTournamentStats(playerId: Int, clickedId: Int) {
        val stats = getViewModel()?.getStats(playerId)
        stats?.observe(this, Observer {
            setUpTournamentStats(it, clickedId)
        })
    }

    private fun setUpTournamentStats(it: Stats?, id: Int) {
        val statsArray = mutableListOf<PlayerStats>()
        val playerStats = it?.playerstats
        playerStats?.let {
            for (player in it) {
                val tournamentId = player.leagueId
                if (id == tournamentId) {
                    statsArray.add(player)
                }
            }
        }
        getViewModel()?.setStats(statsArray)
    }

    private fun displaySeasonStats(playerId: Int, clickedId: Int) {
        val stats = getViewModel()?.getStats(playerId)
        stats?.observe(this, Observer {
            setUpSeasonStats(it, clickedId)
        })
    }

    private fun setUpSeasonStats(it: Stats?, id: Int) {
        val statsArray = mutableListOf<PlayerStats>()
        val playerStats = it?.playerstats
        playerStats?.let {
            for (player in it) {
                val seasonId = player.seasonId
                if (id == seasonId) {
                    statsArray.add(player)

                }
            }
        }
        Log.d("statsArray", statsArray.toString())
        getViewModel()?.setStats(statsArray)

    }

    private fun displayAllTimeStats(playerId: Int) {

        val stats = getViewModel()?.getStats(playerId)
        stats?.observe(this, Observer {
            setUpStat(it)
        })
    }

    private fun setUpStat(it: Stats?) {
        val playerStats = it?.playerstats
        getViewModel()?.setStats(playerStats)
    }

    private fun displayTeamStats(playerId: Int, clickedId: Int) {

        val stats = getViewModel()?.getStats(playerId)
        stats?.observe(this, Observer {
            setUpStats(it, clickedId)
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


    private fun setUpStats(it: Stats?, id: Int?) {
        val statsArray = mutableListOf<PlayerStats>()
        val playerStats = it?.playerstats
        playerStats?.let {
            for (player in it) {
                val teamId = player.teamId
                if (id == teamId) {
                    statsArray.add(player)

                }
            }
        }
        Log.d("statsArray", statsArray.toString())
        getViewModel()?.setStats(statsArray)


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
