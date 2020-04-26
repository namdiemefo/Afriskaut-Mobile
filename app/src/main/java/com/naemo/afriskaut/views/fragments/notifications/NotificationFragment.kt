package com.naemo.afriskaut.views.fragments.notifications

import android.os.Bundle
import android.view.View
import com.naemo.afriskaut.BR

import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.NotificationFragmentBinding
import com.naemo.afriskaut.views.base.BaseFragment
import javax.inject.Inject

class NotificationFragment : BaseFragment<NotificationFragmentBinding, NotificationViewModel>(),
    NotificationNavigator {

    var notificationViewModel: NotificationViewModel? = null
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


}
