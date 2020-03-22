package com.naemo.afriscout.views.activities.main

import android.os.Bundle
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.ActivityMainBinding
import com.naemo.afriscout.views.base.BaseActivity
import com.naemo.afriscout.views.fragments.home.HomeFragment
import com.naemo.afriscout.views.fragments.notifications.NotificationFragment
import com.naemo.afriscout.views.fragments.profile.ProfileFragment
import com.naemo.afriscout.views.fragments.search.SearchFragment
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    MainNavigator {

    var mainViewModel: MainViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_main
        @Inject set

    var mBinder: ActivityMainBinding? = null

    var profileFragment = ProfileFragment()
    @Inject set

    var notificationFragment =
        NotificationFragment()
    @Inject set

    var searchFragment = SearchFragment()
    @Inject set

    var homeFragment = HomeFragment()



    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainViewModel? {
        return mainViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
        navigate()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = mainViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
        initViews()
    }

    private fun initViews() {
        mBinder?.viewPager?.offscreenPageLimit = 4
        mBinder?.viewPager?.setPagingEnabled(true)

        val pagerAdapter =
            BottomBarAdapter(supportFragmentManager)

        pagerAdapter.addFragments(homeFragment)
        pagerAdapter.addFragments(searchFragment)
        pagerAdapter.addFragments(notificationFragment)
        pagerAdapter.addFragments(profileFragment)
        mBinder?.viewPager?.adapter = pagerAdapter
    }

    private fun navigate() {
        mBinder?.navigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.feeds -> {
                    mBinder?.viewPager?.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    mBinder?.viewPager?.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notifications -> {
                    mBinder?.viewPager?.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    mBinder?.viewPager?.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }


  /*  override fun onBackPressed() {
        if (view_pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            view_pager.currentItem = view_pager.currentItem - 1
        }
    }*/

}
