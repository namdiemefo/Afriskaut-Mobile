package com.naemo.afriskaut.views.fragments.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.SearchFragmentBinding
import com.naemo.afriskaut.db.local.room.search.Player
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainer
import com.naemo.afriskaut.views.adapters.SearchAdapter
import com.naemo.afriskaut.views.base.BaseFragment
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>(), SearchNavigator, SearchAdapter.ItemClicklistener,
    CoroutineScope {

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
        getViewModel()?.save()
    }

    override fun retrieveFromDb() {
        launch {
            val players =  getViewModel()?.retrieveSearchResults()
            setUpPlayerSearchResult(players)
        }

    }

    private fun setUpPlayerSearchResult(it: List<Player>?) {
        val adapter = it?.let { it1 -> SearchAdapter(requireContext().applicationContext,
                it1, this)
        }
        search_results.adapter = adapter
        search_results.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils.showSnackBar(requireActivity().applicationContext, search_frame, msg)
    }

    override fun showSpin() {
        appUtils.showDialog(requireContext())
    }

    override fun hideSpin() {
        appUtils.cancelDialog()
    }

    override fun load(player: List<Player>) {
        val adapter = SearchAdapter(requireContext().applicationContext, player, this)
        search_results.adapter = adapter
        search_results.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onItemClicked(player: Player) {
        val intent = Intent(requireContext(), FragmentContainer::class.java)
        intent.putExtra("player", player)
        startActivity(intent)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}
