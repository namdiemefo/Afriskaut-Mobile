package com.naemo.afriscout.views.activities.pages.playerprofile

import android.os.Bundle
import android.util.Log
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityPlayerProfileBinding
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.activities.pages.playerprofile.PlayerProfileNavigator
import com.naemo.afriscout.views.activities.pages.playerprofile.PlayerProfileViewModel
import com.naemo.afriscout.views.base.BaseActivity
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
        val id = intent.getStringExtra("id")
        val img = intent.getStringExtra("img")
        val name = intent.getStringExtra("name")
        val height = intent.getStringExtra("height")
        val dob = intent.getStringExtra("dob")
        val team = intent.getStringExtra("team")
        val nationality = intent.getStringExtra("nationality")
        val position = intent.getStringExtra("position")
        val following = intent.getBooleanExtra("following", false)
        Log.d("id", id!!)
        makeNetworkCall(id, img, name, height, dob, team, nationality, position, following)

    }

    private fun makeNetworkCall(id: String?, img: String?, name: String?, height: String?, dob: String?, team: String?, nationality: String?, position: String?, following: Boolean?) {
        getViewModel()?.makeCall(id!!, img!!, name!!, height!!, dob!!, team!!, nationality!!, position!!, following!!)
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
        val id = intent.getStringExtra("id")
        getViewModel()?.follow(id!!)
    }

    override fun goBack() {
        onBackPressed()
    }
}
