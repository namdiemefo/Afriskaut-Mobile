package com.naemo.afriskaut.views.activities.pages.suggest

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.ActivitySuggestionBinding
import com.naemo.afriskaut.db.local.room.suggest.SuggestData
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainer
import com.naemo.afriskaut.views.adapters.SuggestionAdapter
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_suggestion.*
import javax.inject.Inject

class SuggestionActivity : BaseActivity<ActivitySuggestionBinding, SuggestionViewModel>(), SuggestionNavigator, SuggestionAdapter.ItemClicklistener {

    var suggestionViewModel: SuggestionViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_suggestion
    @Inject set

    var mBinder: ActivitySuggestionBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        hideToolBar()
        super.onCreate(savedInstanceState)
        doBinding()
        initViews()
    }

    private fun initViews() {
        val intent = intent
        val players = intent.getParcelableArrayListExtra<SuggestData>("players")
        displayPlayers(players)
//        getViewModel()?.retrieveSuggestion()?.observe(this, Observer {
//            display(it)
//        })
    }

    private fun displayPlayers(players: ArrayList<SuggestData>?) {
        val player = players?.toList()
        val adapter = player?.let { it1 -> SuggestionAdapter(this, it1, this) }
        suggestion_recycler.adapter = adapter
        suggestion_recycler.layoutManager = LinearLayoutManager(this)

    }


    private fun display(it: List<SuggestData>?) {
        val adapter = it?.let { it1 -> SuggestionAdapter(this, it1, this) }
        suggestion_recycler.adapter = adapter
        suggestion_recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = suggestionViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SuggestionViewModel? {
        return suggestionViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun onItemClicked(player: SuggestData) {
        val intents = Intent(this, FragmentContainer::class.java)
        val players = player.player()
        intents.putExtra("player", players)
        startActivity(intents)
    }

    fun SuggestData.player() = com.naemo.afriskaut.db.local.room.search.Player(
        age = age,
        birthcountry = birthcountry,
        birthdate = birthdate,
        birthplace = birthplace,
        countryId = countryId,
        displayName = displayName,
        dob = dob,
        following = following,
        fullname = fullname,
        height = height,
        id = id,
        imagePath = imagePath,
        nationality = nationality,
        position = position,
        score = score,
        stats = stats,
        weight = weight
    )
}
