package com.naemo.afriskaut.views.activities.pages.playerstats


import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivityFragmentContainerBinding
import com.naemo.afriskaut.db.local.room.search.Player
import com.naemo.afriskaut.views.activities.main.MainActivity
import com.naemo.afriskaut.views.base.BaseActivity
import com.naemo.afriskaut.views.fragments.player.pickstats.PickStatsFragmentDirections
import com.naemo.afriskaut.views.fragments.player.playerinfo.PlayerInfoFragmentDirections
import com.naemo.afriskaut.views.fragments.player.stats.StatsFragmentDirections
import javax.inject.Inject

class FragmentContainer : BaseActivity<ActivityFragmentContainerBinding, FragmentContainerViewModel>(),
    FragmentContainerNavigator {


    var fragmentContainerViewModel: FragmentContainerViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_fragment_container
        @Inject set

    var mBinder: ActivityFragmentContainerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        doBinding()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = fragmentContainerViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    private fun initViews() {
        val intent = intent
        val player = intent.getParcelableExtra<Player>("player")

        val bundle = Bundle()
        bundle.putParcelable("player", player)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.main_nav, bundle)
    }

    override fun navigateToStatsPage(player: Player) {
        val action = PickStatsFragmentDirections.actionPickStatsFragmentToStatsFragment(player)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun navigateToPickStatsPage(player: Player) {
        val action = StatsFragmentDirections.actionStatsFragmentToPickStatsFragment(player)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun fromInfoToPickStats(player: Player) {
        val action = PlayerInfoFragmentDirections.actionPlayerInfoFragmentToPickStatsFragment(player)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun fromPickStatsToInfo(player: Player) {
        val action = PickStatsFragmentDirections.actionPickStatsFragmentToPlayerInfoFragment(player)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun moveBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): FragmentContainerViewModel? {
        return fragmentContainerViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

}

