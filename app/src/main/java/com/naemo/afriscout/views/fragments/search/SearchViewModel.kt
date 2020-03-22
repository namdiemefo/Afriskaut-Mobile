package com.naemo.afriscout.views.fragments.search

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriscout.db.local.room.search.Data
import com.naemo.afriscout.db.local.room.search.SearchRepository
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : BaseViewModel<SearchNavigator>(application) {

    var search = ObservableField("")
    private var repository: SearchRepository? = null
    val TAG = "SearchViewModel"

    init {
        repository = SearchRepository(application)
    }

    fun retrieveSearchResults(): LiveData<Data>? {
        Log.d(TAG, "GETTING THE SEARCH RESULTS")
        return repository?.loadSearchResults()
    }

    fun saveSearchResults() = CoroutineScope(Main).launch {
        Log.d(TAG, "SAVING THE SEARCH RESULTS")
        val query = search.get().toString()
        Log.d(TAG, query)
        repository?.saveSearchResults(query)
    }
}

interface SearchNavigator {
    fun sendSearch()

    fun showSnackBarMessage(msg: String)
}

@Module
class SearchModule {

    @Provides
    fun providesSearchViewModel(application: Application): SearchViewModel {
        return SearchViewModel(application)
    }
}
