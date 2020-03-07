package com.naemo.afriscout.views.search

import android.app.Application
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class SearchViewModel(application: Application) : BaseViewModel<SearchNavigator>(application) {
    // TODO: Implement the ViewModel
}

interface SearchNavigator {

}

@Module
class SearchModule {

    @Provides
    fun providesSearchViewModel(application: Application): SearchViewModel {
        return SearchViewModel(application)
    }
}
