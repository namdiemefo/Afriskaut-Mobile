package com.naemo.afriscout.views.activities.pages.playerstats.statspage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityStatsPageBinding
import com.naemo.afriscout.db.local.room.stats.PlayerStats
import com.naemo.afriscout.db.local.room.stats.Stats
import com.naemo.afriscout.db.local.room.stats.StatsRepository
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.activities.pages.playerstats.allstats.AllStatsActivity
import com.naemo.afriscout.views.activities.pages.playerstats.pickclub.PickClubActivity
import com.naemo.afriscout.views.base.BaseActivity
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
        val stat = getViewModel()?.getPlayerStat()
        stat?.observe(this, Observer {
          //  getTeams(it)
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

    private fun sendIds(array: ArrayList<Int>?, typeArray: ArrayList<String>?) {
        Log.d("stuff3", array.toString())
        val intent = Intent(this, PickClubActivity::class.java)
        intent.putIntegerArrayListExtra("teamIds", array)
        intent.putStringArrayListExtra("type", typeArray)
        startActivity(intent)
    }

    private fun getTeams(it: Stats?) {
        val array = it?.playerstats
        array?.let {
            for (i in array) {
                val id = i.teamId
                Log.d("teamId", id.toString())
            }
        }
        val ss = it?.playerStatsId
        Log.d("ss", ss!!)
        Log.d("timm", it.playerStatsId.toString())
    }

    override fun goToAllTime() {
        startActivity(Intent(this, AllStatsActivity::class.java))
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
