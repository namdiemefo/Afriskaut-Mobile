package com.naemo.afriscout.views.account.register

import android.annotation.TargetApi
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.naemo.afriscout.R
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.TextView
import android.view.ViewGroup
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat



class RegisterActivity : AppCompatActivity(){

    internal var roles = arrayOf("--Select profession--", "Agent", "Scout", "Federation", "Player", "Commentator", "Analyst")
    lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var stateSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        doBinding()
    }

    private fun doBinding() {
        initView()
    }

    private fun initView() {
        arrayAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, roles) {
            override fun isEnabled(position: Int): Boolean {
                return if (position == 0) {
                    return false
                } else true
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
               val view = super.getView(position, convertView, parent)
                val typeface = ResourcesCompat.getFont(this@RegisterActivity, R.font.montserrat)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(getColor(R.color.colorHint))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                } else {
                    tv.setTextColor(getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                }
                return view
            }

            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val typeface = ResourcesCompat.getFont(this@RegisterActivity, R.font.montserrat)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(getColor(R.color.colorHint))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                } else {
                    tv.setTextColor(getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)

                }
                return view
            }
        }
        role.adapter = arrayAdapter
        role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                val tv = p0


            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                stateSelected = roles[p2]
            }

        }
    }
}
