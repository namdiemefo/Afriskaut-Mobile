package com.naemo.afriskaut.views.activities.pages.radar

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityRadarBinding
import com.naemo.afriskaut.db.local.room.following.FollowingData
import com.naemo.afriskaut.db.local.room.search.Player
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainer
import com.naemo.afriskaut.views.adapters.RadarAdapter
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_radar.*
import javax.inject.Inject

class RadarActivity : BaseActivity<ActivityRadarBinding, RadarViewModel>(), RadarNavigator,
RadarAdapter.ItemClickListener{

    var radarViewModel: RadarViewModel? = null
        @Inject set

    var mBinder: ActivityRadarBinding? = null

    var appUtils: AppUtils? = null
        @Inject set

    var mLayoutId = R.layout.activity_radar
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()

    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = radarViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
        initViews()
    }

    private fun initViews() {
        getViewModel()?.makeFollowersCall()
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): RadarViewModel? {
        return radarViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun sendSearch() {
       val search = getViewModel()?.makeSearch()
        search?.observe(this, Observer {
            setRadarUp(it)
        })
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(this, radar_frame, msg)
    }

    override fun loadFromDb() {
       val radar = getViewModel()?.retrieveRadar()
        radar?.observe(this, Observer {
            setRadarUp(it)
        })
    }

    override fun load(data: List<FollowingData>) {
        val adapter = RadarAdapter(this, data, this)
        radar_recycler.adapter = adapter
        radar_recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun setRadarUp(it: List<FollowingData>?) {
        val adapter = it?.let { it1 -> RadarAdapter(this, it1, this) }
        radar_recycler.adapter = adapter
        radar_recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClicked(player: FollowingData) {
        val intent = Intent(this, FragmentContainer::class.java)
        val newPlayer = player.player()
        intent.putExtra("player", newPlayer)
        startActivity(intent)
    }

    fun FollowingData.player() = Player(
        birthcountry = birthcountry,
        birthdate = birthdate,
        birthplace = birthplace,
        commonName = commonName,
        countryId = countryId,
        displayName = displayName,
        firstname = firstname,
        fullname = fullname,
        height = height,
        id = id,
        imagePath = imagePath,
        lastname = lastname,
        nationality = nationality,
        playerId = playerId,
        position = position,
        positionId = positionId,
        stats = stats,
        teamId = teamId,
        teamName = teamName,
        weight = weight,
        following = following
    )


}
