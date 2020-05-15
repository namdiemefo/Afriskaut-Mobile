package com.naemo.afriskaut.views.activities.pages.playerprofile

import android.content.Intent
import android.os.Bundle
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityPlayerProfileBinding
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.statspage.StatsPageActivity
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_player_profile.*
import javax.inject.Inject

class PlayerProfileActivity : BaseActivity<ActivityPlayerProfileBinding, PlayerProfileViewModel>(),
    PlayerProfileNavigator {

    var playerProfileViewModel: PlayerProfileViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_player_profile
    @Inject set

    var appUtils = AppUtils()
        @Inject set

    var mBinder: ActivityPlayerProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()

    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = playerProfileViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
        initViews()
    }

    private fun initViews() {
        val intent = intent
        val img = intent.getStringExtra("img")
        val name = intent.getStringExtra("name")
        val height = intent.getStringExtra("height")
        val dob = intent.getStringExtra("dob")
        val team = intent.getStringExtra("team")
        val nationality = intent.getStringExtra("nationality")
        val position = intent.getStringExtra("position")
        val following = intent.getBooleanExtra("following", false)
        makeNetworkCall(img, name, height, dob, team, nationality, position, following)

    }

    private fun makeNetworkCall(img: String?, name: String?, height: String?, dob: String?, team: String?, nationality: String?, position: String?, following: Boolean?) {
        getViewModel()?.makeCall(img!!, name!!, height!!, dob!!, team!!, nationality!!, position!!, following!!)
    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): PlayerProfileViewModel? {
        return playerProfileViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils.showSnackBar(this, container, msg)
    }

    override fun showSpin() {
        appUtils.showDialog(this)
    }

    override fun hideSpin() {
        appUtils.cancelDialog()
    }

    override fun followPlayer() {
        val intent = intent
        val dBid = intent.getIntExtra("dBid", 0)
        val id = intent.getStringExtra("id")
        val buttonText = follow_button.text
        if (buttonText == "Follow") {
           id?.let { getViewModel()?.follow(it, dBid)  }
        } else {
           id?.let { getViewModel()?.unfollow(it, dBid) }
        }

    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToStatsPage() {
        val intent = intent
        val playerId = intent.getIntExtra("playerId", 0)
        val intents = Intent(this, StatsPageActivity::class.java)
        intents.putExtra("playerId", playerId)
        startActivity(intents)
    }
}
