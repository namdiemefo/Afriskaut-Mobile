package com.naemo.afriscout.views.activities.pages

import android.os.Bundle
import android.util.Log
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityPlayerProfileBinding
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_player_profile.*
import javax.inject.Inject

class PlayerProfileActivity : BaseActivity<ActivityPlayerProfileBinding, PlayerProfileViewModel>(), PlayerProfileNavigator {

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
       // setContentView(R.layout.activity_player_profile)
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
        Log.d("id", id!!)
        makeNetworkCall(id)

    }

    private fun makeNetworkCall(id: String) {
        getViewModel()?.makeCall(id)
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
