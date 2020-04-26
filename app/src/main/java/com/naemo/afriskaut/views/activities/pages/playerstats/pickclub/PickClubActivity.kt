package com.naemo.afriskaut.views.activities.pages.playerstats.pickclub

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityPickClubPageBinding
import com.naemo.afriskaut.db.local.room.team.Team
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.allstats.AllStatsActivity
import com.naemo.afriskaut.views.adapters.LeagueAdapter
import com.naemo.afriskaut.views.adapters.NationAdapter
import com.naemo.afriskaut.views.adapters.SeasonAdapter
import com.naemo.afriskaut.views.adapters.TeamAdapter
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_club_page.*
import javax.inject.Inject

class PickClubActivity : BaseActivity<ActivityPickClubPageBinding, PickClubViewModel>(), PickClubNavigator,
    TeamAdapter.ItemClickListener, NationAdapter.ItemClickListener, SeasonAdapter.ItemClickListener,
 LeagueAdapter.ItemClickListen{

    var appUtils: AppUtils? = null
        @Inject set

    var pickClubViewModel: PickClubViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_pick_club_page
        @Inject set

    var mBinder: ActivityPickClubPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
        initViews()
    }

    private fun initViews() {
        val intent = intent
        when (intent.getIntExtra("pick", 0)) {
            1 -> {
                forTeams()
            }
            2 -> {
                the_title.text = getString(R.string.comps)
                recycler_layout.visibility = View.GONE
                forTournaments()
            }
            3 -> {
                the_title.text = getString(R.string.seasons)
                recycler_layout.visibility = View.GONE
                forSeasons()
            }
        }

    }

    private fun forSeasons() {
        val intent = intent
        val ids = intent?.getIntegerArrayListExtra("seasonIds")
        getViewModel()?.getSeasonName(ids)
    }

    private fun forTournaments() {
        val intent = intent
        val ids = intent?.getIntegerArrayListExtra("leagueIds")
        getViewModel()?.getLeagueName(ids)
    }

    private fun forTeams() {
        val intent = intent
        val ids = intent?.getIntegerArrayListExtra("teamIds")
        getViewModel()?.getTeamName(ids)
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = pickClubViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): PickClubViewModel? {
        return pickClubViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(this, pick_club_frame, msg)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun retrieveTeams(team: Team?) {

        val clubAdapter = team?.let { TeamAdapter(this, it, this) }
        club_recycler_view.adapter = clubAdapter
        club_recycler_view.layoutManager = LinearLayoutManager(this)

        val countryAdapter = team?.let { NationAdapter(this, it, this) }
        nation_recycler_view.adapter = countryAdapter
        nation_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun retrieveLeagues(team: Team?) {

        val leagueAdapter = team?.let { LeagueAdapter(this, it, this) }
        nation_recycler_view.adapter = leagueAdapter
        nation_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun retrieveSeason(seasonName: Map<Int, String>?) {

        val seasonAdapter = SeasonAdapter(this, seasonName, this)
        nation_recycler_view.adapter = seasonAdapter
        nation_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClicked(id: Int) {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, AllStatsActivity::class.java)
        intents.putExtra("allStats", 1)
        intents.putExtra("playerId", playerId)
        intents.putExtra("clickedId", id)
        startActivity(intents)
    }

    override fun onItemClicked(id: Int?) {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, AllStatsActivity::class.java)
        intents.putExtra("allStats", 3)
        intents.putExtra("playerId", playerId)
        intents.putExtra("clickedId", id)
        startActivity(intents)
    }

    override fun onItemClick(id: Int?) {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, AllStatsActivity::class.java)
        intents.putExtra("allStats", 2)
        intents.putExtra("playerId", playerId)
        intents.putExtra("clickedId", id)
        startActivity(intents)
    }


}

