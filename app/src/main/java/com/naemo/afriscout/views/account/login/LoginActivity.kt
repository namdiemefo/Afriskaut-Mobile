package com.naemo.afriscout.views.account.login

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityLoginBinding
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseActivity
import com.naemo.afriscout.views.main.MainActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

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
        getViewModel()?.loadLogin(application)
    }

    override fun showToast(msg: String) {
       Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun goToForgot() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = loginViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }
}
