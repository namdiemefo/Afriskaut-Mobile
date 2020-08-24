package com.naemo.afriskaut.views.activities.pages.scout

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityScoutBinding
import com.naemo.afriskaut.db.local.room.scout.Player
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainer
import com.naemo.afriskaut.views.adapters.ScoutAdapter
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scout.*
import javax.inject.Inject

class ScoutActivity : BaseActivity<ActivityScoutBinding, ScoutViewModel>(), ScoutAdapter.ItemClicklistener {

    var scoutViewModel: ScoutViewModel? = null
        @Inject set

    var mBinder: ActivityScoutBinding? = null

    var appUtils: AppUtils? = null
        @Inject set

    var mLayoutId = R.layout.activity_scout
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {

        getViewModel()?.retrievePlayers()?.observe(this, Observer {
            display(it)
        })


    }

    private fun display(player: List<Player>) {
        val scoutAdapter = ScoutAdapter(this, player, this)
        scout_recycler.adapter = scoutAdapter
        scout_recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClicked(player: Player) {
        val intents = Intent(this, FragmentContainer::class.java)
        val players = player.player()
        intents.putExtra("player", players)
        startActivity(intents)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ScoutViewModel? {
        return scoutViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    fun Player.player() = following?.let {
        com.naemo.afriskaut.db.local.room.search.Player(
        age = age,
        birthcountry = birthcountry,
        birthdate = birthdate,
        birthplace = birthplace,
        countryId = countryId,
        displayName = displayName,
        dob = dob,
        following = it,
        fullname = fullname,
        height = height,
        id = id,
        imagePath = imagePath,
        nationality = nationality,
        position = position,
        score = score,
        stats = stats,
        weight = weight
    )
    }
}
