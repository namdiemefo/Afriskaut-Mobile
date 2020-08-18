package com.naemo.afriskaut.db.local.room.suggest

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.network.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SuggestionRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var suggestionDao: SuggestionDao? = null
    private val context: Context? = null
    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    init {
        val database = SuggestionDatabase.invoke(application)
        suggestionDao = database.suggestionDao()
    }

    fun loadSuggestData(): LiveData<List<SuggestData>>? {
        return suggestionDao?.loadSuggestion()
    }


    fun saveSuggestData(data: SuggestData) {
        launch {
            save(data)
        }
    }

    private suspend fun save(data: SuggestData) {
        withContext(Dispatchers.IO) {
            suggestionDao?.saveSuggestion(data)
        }
    }

    fun deleteData() {
        launch {
            delete()
        }
    }

    private suspend fun delete() {
        withContext(Dispatchers.IO) {
            suggestionDao?.deleteSuggestion()
        }
    }

}