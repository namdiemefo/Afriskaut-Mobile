package com.naemo.afriscout.views.search

import android.os.Bundle
import android.view.View
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.SearchFragmentBinding
import com.naemo.afriscout.views.base.BaseFragment
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>(), SearchNavigator {

    var searchViewModel: SearchViewModel? = null
    @Inject set

    private var mLayoutId = R.layout.search_fragment
    var mBinding: SearchFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doBinding()
        initViews()
    }

    private fun doBinding() {
        mBinding = getViewDataBinding()
        mBinding?.viewModel = searchViewModel
        mBinding?.navigator = this
        mBinding?.viewModel?.setNavigator(this)
    }

    private fun initViews() {

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): SearchViewModel? {
        return searchViewModel
    }

}
