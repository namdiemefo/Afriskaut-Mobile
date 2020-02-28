package com.naemo.afriscout.views.account.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.naemo.afriscout.R
import com.naemo.afriscout.views.account.login.LoginActivity
import com.naemo.afriscout.views.account.register.RegisterActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_home)
    }

    fun goToRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun goToLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
