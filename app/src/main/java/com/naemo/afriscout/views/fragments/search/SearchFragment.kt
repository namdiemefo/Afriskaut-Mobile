package com.naemo.afriscout.views.fragments.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriscout.BR
import com.naemo.afriscout.R
import com.naemo.afriscout.databinding.SearchFragmentBinding
import com.naemo.afriscout.db.local.room.search.Data
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.activities.account.login.LoginActivity
import com.naemo.afriscout.views.activities.pages.PlayerProfileActivity
import com.naemo.afriscout.views.adapters.SearchAdapter
import com.naemo.afriscout.views.base.BaseFragment
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>(), SearchNavigator, SearchAdapter.ItemClicklistener {

    var searchViewModel: SearchViewModel? = null
    @Inject set

    var appUtils = AppUtils()
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

    override fun sendSearch() {
        getViewModel()?.saveSearchResults()
        getViewModel()?.retrieveSearchResults()?.observe(requireActivity(), Observer {
            setUpPlayerSearchResult(it)
        })
    }

    private fun setUpPlayerSearchResult(it: Data?) {
        val adapter = SearchAdapter(requireContext().applicationContext, it!!, this)
        search_results.adapter = adapter
        search_results.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun showSnackBarMessage(msg: String) {
        appUtils.showSnackBar(requireActivity().applicationContext, search_frame, msg)
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(requireContext(), PlayerProfileActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}
