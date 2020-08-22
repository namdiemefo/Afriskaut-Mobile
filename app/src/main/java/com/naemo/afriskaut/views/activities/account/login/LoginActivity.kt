package com.naemo.afriskaut.views.activities.account.login

import android.content.Intent
import android.os.Bundle
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityLoginBinding
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.account.forgotpassword.ForgotActivity
import com.naemo.afriskaut.views.activities.account.home.HomeActivity
import com.naemo.afriskaut.views.base.BaseActivity
import com.naemo.afriskaut.views.activities.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    LoginNavigator {

    var loginViewModel: LoginViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_login
    @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    var mBinder: ActivityLoginBinding? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): LoginViewModel? {
        return loginViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun sendLogin() {
        hideKeyBoard()
        getViewModel()?.loadLogin()
    }

    override fun showToast(msg: String) {
       appUtils?.showActivityToast(this, msg)
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun hideKeyboard() {
        hideKeyBoard()
    }

    override fun goToForgot() {
        startActivity(Intent(this, ForgotActivity::class.java))
    }

    override fun showSnackBar(msg: String) {
        appUtils?.showSnackBar(this, frame, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = loginViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
