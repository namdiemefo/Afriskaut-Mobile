package com.naemo.afriskaut.views.activities.main

import android.app.Application
import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.naemo.afriskaut.R
import com.naemo.afriskaut.views.base.BaseViewModel
import com.naemo.afriskaut.views.fragments.home.HomeFragment
import com.naemo.afriskaut.views.fragments.notifications.NotificationFragment
import com.naemo.afriskaut.views.fragments.profile.ProfileFragment
import com.naemo.afriskaut.views.fragments.search.SearchFragment
import dagger.Module
import dagger.Provides
import java.util.ArrayList

class MainViewModel(application: Application) : BaseViewModel<MainNavigator>(application) {

}

interface MainNavigator {

}

@Module
class MainModule {

    @Provides
    fun providesMainViewModel(application: Application): MainViewModel {
        return MainViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_main
    }

    @Provides
    fun providesProfileFragment(): ProfileFragment {
        return ProfileFragment()
    }

    @Provides
    fun providesNotificationFragment(): NotificationFragment {
        return NotificationFragment()
    }

    @Provides
    fun providesSearchFragment(): SearchFragment {
        return SearchFragment()
    }

    @Provides
    fun providesHomeFragment(): HomeFragment {
        return HomeFragment()
    }
}

class BottomBarAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {
    private val fragments = ArrayList<Fragment>()

    override fun getCount(): Int {
        return fragments.size
    }

    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}

abstract class SmartFragmentStatePagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val registeredFragments = SparseArray<Fragment>()

    // Register the fragment when the item is instantiated
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.append(position, fragment)
        return fragment
    }

    // Unregister when the item is inactive
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

}
