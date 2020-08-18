package com.naemo.afriskaut.views.fragments.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.naemo.afriskaut.BR

import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.NotificationFragmentBinding
import com.naemo.afriskaut.db.local.room.suggest.SuggestData
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.suggest.SuggestionActivity
import com.naemo.afriskaut.views.base.BaseFragment
import kotlinx.android.synthetic.main.notification_fragment.*
import javax.inject.Inject

class NotificationFragment : BaseFragment<NotificationFragmentBinding, NotificationViewModel>(),
    NotificationNavigator {

    var notificationViewModel: NotificationViewModel? = null
    @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    private var mLayoutId = R.layout.notification_fragment
    var mBinding: NotificationFragmentBinding? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): NotificationViewModel? {
        return notificationViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doBinding()
        initViews()
    }

    private fun doBinding() {
        mBinding = getViewDataBinding()
        mBinding?.viewModel = notificationViewModel
        mBinding?.navigator = this
        mBinding?.viewModel?.setNavigator(this)
    }

    private fun initViews() {

    }

    override fun goToSuggestPage() {
        startActivity(Intent(requireContext(), SuggestionActivity::class.java))
    }

    override fun sendSuggestions(player: List<SuggestData>?) {
        Log.d("players", player.toString())
        val intent = Intent(requireContext(), SuggestionActivity::class.java)
        val players = arrayListOf<SuggestData>()
        player?.let { players.addAll(it) }
        intent.putParcelableArrayListExtra("players", players)
        startActivity(intent)
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(requireContext(), suggestion_frame, msg)
    }

    override fun showSpin() {
        appUtils?.showDialog(requireContext())
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun goToU19() {
        val under = "19"
        getViewModel()?.suggest(under)
    }

    override fun goToU21() {
        val under = "21"
        getViewModel()?.suggest(under)
    }

    override fun goToU23() {
        val under = "23"
        getViewModel()?.suggest(under)
    }


}
