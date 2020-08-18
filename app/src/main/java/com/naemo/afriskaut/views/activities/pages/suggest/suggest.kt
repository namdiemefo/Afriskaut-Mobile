package com.naemo.afriskaut.views.activities.pages.suggest

import android.app.Application
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.R
import com.naemo.afriskaut.db.local.room.suggest.SuggestData
import com.naemo.afriskaut.db.local.room.suggest.SuggestionRepository
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class SuggestionViewModel(application: Application) : BaseViewModel<SuggestionNavigator>(application) {

    private var repository: SuggestionRepository = SuggestionRepository(application)

    fun retrieveSuggestion() : LiveData<List<SuggestData>>? {
        return repository.loadSuggestData()
    }

}

interface SuggestionNavigator

@Module
class SuggestionModule {

    @Provides
    fun providesSuggestionViewModel(application: Application): SuggestionViewModel {
        return SuggestionViewModel(application)
    }


    @Provides
    fun layoutId() : Int {
        return R.layout.activity_suggestion
    }
}

