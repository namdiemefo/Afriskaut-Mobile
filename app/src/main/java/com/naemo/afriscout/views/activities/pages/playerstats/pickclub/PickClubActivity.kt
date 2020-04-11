package com.naemo.afriscout.views.activities.pages.playerstats.pickclub

import android.os.Bundle
import android.util.Log
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityPickClubPageBinding
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_club_page.*
import javax.inject.Inject

class PickClubActivity : BaseActivity<ActivityPickClubPageBinding, PickClubViewModel>(), PickClubNavigator {

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
        val ids = intent?.getIntegerArrayListExtra("teamIds")
        val type = intent?.getStringArrayListExtra("type")
        Log.d("stuff4", ids?.get(0)?.toString()!!)
        Log.d("stuff5", ids.toString())
        getViewModel()?.getTeamName(ids, type)
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
}
