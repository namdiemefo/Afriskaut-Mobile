package com.naemo.afriskaut.views.activities.pages.radar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityRadarBinding
import com.naemo.afriskaut.db.local.room.following.FollowingData
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerprofile.PlayerProfileActivity
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
        Log.d("failure1", "hidespin")
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

    override fun onItemClicked(
        dBid: Int?,
        id: String?,
        img: String?,
        playerId: Int?,
        name: String?,
        height: String?,
        dob: String?,
        team: String?,
        nationality: String?,
        position: String?,
        Follow: Boolean?
    ) {
        val intent = Intent(this, PlayerProfileActivity::class.java)
        intent.putExtra("dBid", dBid)
        intent.putExtra("id", id)
        intent.putExtra("img", img)
        intent.putExtra("playerId", playerId)
        intent.putExtra("name", name)
        intent.putExtra("height", height)
        intent.putExtra("dob", dob)
        intent.putExtra("team", team)
        intent.putExtra("nationality", nationality)
        intent.putExtra("position", position)
        intent.putExtra("following", Follow)
        startActivity(intent)
    }

}
