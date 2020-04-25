package com.naemo.afriscout.views.activities.account.register

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.naemo.afriscout.R
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.TextView
import android.view.ViewGroup
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.naemo.afriscout.BR
import com.naemo.afriscout.databinding.ActivityRegisterBinding
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.activities.account.home.HomeActivity
import com.naemo.afriscout.views.activities.account.login.LoginActivity
import com.naemo.afriscout.views.base.BaseActivity
import com.naemo.afriscout.views.activities.main.MainActivity
import javax.inject.Inject


class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),
    RegisterNavigator {

    var registerViewModel: RegisterViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_register
    @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    var mBinder: ActivityRegisterBinding? = null


    internal var roles = arrayOf("--Select role--", "Fan", "Agent", "Scout", "Federation", "Player", "Commentator", "Analyst")
    private lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var roleSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
        initView()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = registerViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)

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

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                roleSelected = roles[p2]
            }

        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): RegisterViewModel? {
        return registerViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun sendRegister() {
        hideKeyBoard()
        getViewModel()?.loadRegister(roleSelected)
    }

    override fun showToast(msg: String) {
        appUtils?.showActivityToast(this, msg)
    }

    override fun showSnackBar(msg: String) {
        appUtils?.showSnackBar(this, register_frame, msg)
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun goToPp() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun goToTos() {

    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

}
