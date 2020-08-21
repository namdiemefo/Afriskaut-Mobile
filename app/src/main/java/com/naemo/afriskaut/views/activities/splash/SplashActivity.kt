package com.naemo.afriskaut.views.activities.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.naemo.afriskaut.R
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.views.activities.account.home.HomeActivity
import com.naemo.afriskaut.views.activities.main.MainActivity
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    var appPreferences = AppPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
       

        Handler().postDelayed({
            val user = appPreferences.getUser()
            val token = user.jwt_token
            Log.d("token", token)
            if (token.isNullOrEmpty()) {
                goToMainActivity()
            } else {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }, 2000)
        
    }
     private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
