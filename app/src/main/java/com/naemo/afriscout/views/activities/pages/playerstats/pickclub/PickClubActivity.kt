package com.naemo.afriscout.views.activities.pages.playerstats.pickclub

import android.os.Bundle
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityPickClubPageBinding
import com.naemo.afriscout.views.base.BaseActivity
import javax.inject.Inject

class PickClubActivity : BaseActivity<ActivityPickClubPageBinding, PickClubViewModel>(), PickClubNavigator {

    var pickClubViewModel: PickClubViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_pick_club_page
        @Inject set

    var mBinder: ActivityPickClubPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
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
}
