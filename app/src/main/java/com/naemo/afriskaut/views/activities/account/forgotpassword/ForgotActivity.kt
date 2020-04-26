package com.naemo.afriskaut.views.activities.account.forgotpassword

import android.content.Intent
import android.os.Bundle
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityForgotBinding
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.account.login.LoginActivity
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_forgot.*
import javax.inject.Inject

class ForgotActivity : BaseActivity<ActivityForgotBinding, ForgotViewModel>(),
    ForgotNavigator {

    var forgotViewModel: ForgotViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_forgot
        @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    var mBinder: ActivityForgotBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = forgotViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ForgotViewModel? {
        return forgotViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun sendToken() {
        hideKeyBoard()
        getViewModel()?.loadToken()
    }

    override fun showSnackBar(msg: String) {
        appUtils?.showSnackBar(this, forgot_frame, msg)
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }
}
