package com.naemo.afriscout.views.activities.pages.playerstats.pickclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.naemo.afriscout.R

class PickClubPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_club_page)
    }

    private fun hideToolBar() {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun onBack() {

    }
}
