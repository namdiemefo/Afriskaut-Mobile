package com.naemo.afriscout.views.activities.account.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.naemo.afriscout.R
import com.naemo.afriscout.views.activities.account.login.LoginActivity
import com.naemo.afriscout.views.activities.account.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(),
    HomeNavigator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_home)
        initViews()
    }

    private fun initViews() {
        register.setOnClickListener { goToRegister() }
        login.setOnClickListener { goToLogin() }
    }

    override fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}
