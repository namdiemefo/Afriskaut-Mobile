package com.naemo.afriskaut.views.activities.pages.playerstats.statspage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityStatsPageBinding
import com.naemo.afriskaut.db.local.room.stats.PlayerStats
import com.naemo.afriskaut.db.local.room.stats.Stats
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.allstats.AllStatsActivity
import com.naemo.afriskaut.views.activities.pages.playerstats.pickclub.PickClubActivity
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stats_page.*
import javax.inject.Inject

class StatsPageActivity : BaseActivity<ActivityStatsPageBinding, StatsPageViewModel>(),
    StatsPageNavigator {

    var statsPageViewModel: StatsPageViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_stats_page
        @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    var mBinder: ActivityStatsPageBinding? = null
    lateinit var teams : PlayerStats
    val player: Stats? = null

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
        initViews()
    }

    private fun initViews() {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        makeStatsCall(playerId)
    }

    private fun makeStatsCall(playerId: Int) {
        getViewModel()?.getPlayerStats(playerId)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun pickClub() {
        val newArray = ArrayList<Int>()
        val typeArray = ArrayList<String>()
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val stat = getViewModel()?.getPlayerStat(playerId)
        stat?.observe(this, Observer {
            val listStats = it.playerstats
            val stuff = listStats?.get(1)?.appearences
            Log.d("stuff", stuff?.toString()!!)
            listStats.let { it1 ->
                for (i in it1) {

                    teams = i
                    Log.d("stuff1", teams.teamId.toString())
                    val ids = teams.teamId
                    Log.d("stuff2", ids?.toString()!!)
                    val type = teams.type
                    newArray.add(ids)
                    typeArray.add(type!!)

                }
            }
            sendIds(newArray, typeArray)
        })


    }

    override fun pickLeague() {
        val leagueArray = ArrayList<Int>()
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val stat = getViewModel()?.getPlayerStat(playerId)
        stat?.observe(this, Observer { it1 ->
            val playerStats = it1.playerstats
            playerStats?.let {
                for (i in it) {
                    val league = i.leagueId
                    league?.let { it2 -> leagueArray.add(it2) }
                }
            }
            sendLeagueIds(leagueArray)
        })

    }

    private fun sendLeagueIds(leagueArray: ArrayList<Int>) {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, PickClubActivity::class.java)
        intents.putExtra("playerId", playerId)
        intents.putIntegerArrayListExtra("leagueIds", leagueArray)
        intents.putExtra("pick", 2)
        startActivity(intents)
    }

    override fun pickSeason() {
        val seasonArray = ArrayList<Int>()
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val stat = getViewModel()?.getPlayerStat(playerId)
        stat?.observe(this, Observer { it1 ->
            val playerStats = it1.playerstats
            playerStats?.let {
                for (i in it) {
                    val season = i.seasonId
                    season?.let { it2 -> seasonArray.add(it2) }
                }
            }
            Log.d("season", seasonArray.toString())
            sendSeasonIds(seasonArray)
        })
    }

    private fun sendSeasonIds(seasonArray: ArrayList<Int>) {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, PickClubActivity::class.java)
        intents.putExtra("playerId", playerId)
        intents.putIntegerArrayListExtra("seasonIds", seasonArray)
        intents.putExtra("pick", 3)
        startActivity(intents)
    }

    private fun sendIds(array: ArrayList<Int>?, typeArray: ArrayList<String>?) {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        Log.d("stuff3", array.toString())
        val intents = Intent(this, PickClubActivity::class.java)
        intents.putExtra("playerId", playerId)
        intents.putIntegerArrayListExtra("teamIds", array)
        intents.putStringArrayListExtra("type", typeArray)
        intents.putExtra("pick", 1)
        startActivity(intents)
    }

    override fun goToAllTime() {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, AllStatsActivity::class.java)
        intents.putExtra("playerId", playerId)
        intents.putExtra("allStats", 4)
        startActivity(intents)
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(this, stats_frame, msg)
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
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
