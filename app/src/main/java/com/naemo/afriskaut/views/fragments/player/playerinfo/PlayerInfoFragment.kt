package com.naemo.afriskaut.views.fragments.player.playerinfo


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.FragmentPlayerInfoBinding
import com.naemo.afriskaut.db.local.room.search.Player
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainerNavigator
import com.naemo.afriskaut.views.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_player_info.*
import javax.inject.Inject

class PlayerInfoFragment : BaseFragment<FragmentPlayerInfoBinding, PlayerInfoViewModel>(), PlayerInfoNavigator {

    private lateinit var fragmentNavigator: FragmentContainerNavigator

    var playerInfoViewModel: PlayerInfoViewModel? = null
        @Inject set

    var mLayoutId = R.layout.fragment_player_info

    var appUtils = AppUtils()
        @Inject set

    var mBinder: FragmentPlayerInfoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doBinding()
        initViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigator = context as FragmentContainerNavigator
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = playerInfoViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    private fun initViews() {
        val args by navArgs<PlayerInfoFragmentArgs>()
        val player = args.player
        fillPlayerInfo(player)

        val backButton: TextView = mBinder?.backButton!!
        backButton.setOnClickListener {
            fragmentNavigator.moveBack()
        }
    }

    private fun fillPlayerInfo(player: Player) {
        getViewModel()?.fillFilelds(player)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): PlayerInfoViewModel? {
        return playerInfoViewModel
    }

    override fun showSpin() {
        appUtils.showDialog(requireContext())
    }

    override fun hideSpin() {
        appUtils.cancelDialog()
    }

    override fun moveToPickStats() {
        val args by navArgs<PlayerInfoFragmentArgs>()
        val player = args.player
        fragmentNavigator.fromInfoToPickStats(player)
    }

    override fun followPlayer() {
        val args by navArgs<PlayerInfoFragmentArgs>()
        val player = args.player
        val dBid = player.id
        val buttonText = follow_button.text.toString()
        if (buttonText == "Follow") {
            dBid.let { it?.let { it1 -> getViewModel()?.follow(it1) } }
        } else {
            dBid.let { it?.let { it1 -> getViewModel()?.unfollow(it1) } }
        }

    }

    override fun showSnackBarMessage(msg: String) {
        appUtils.showSnackBar(requireContext(), container, msg)
    }

    override fun goBack() {
        fragmentNavigator.moveBack()
    }


}
