package com.naemo.afriskaut.views.fragments.notifications

import android.app.Application
import com.naemo.afriskaut.api.models.suggestion.SuggestionRequest
import com.naemo.afriskaut.api.models.suggestion.SuggestionResponse
import com.naemo.afriskaut.db.local.preferences.AppPreferences
import com.naemo.afriskaut.db.local.room.suggest.SuggestData
import com.naemo.afriskaut.db.local.room.suggest.SuggestionRepository
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NotificationViewModel(application: Application) : BaseViewModel<NotificationNavigator>(application) {

    var client = Client()
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    private var repository: SuggestionRepository = SuggestionRepository(application)

    fun suggest(under: String) {

        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = SuggestionRequest(under)

        val suggestCall: Call<SuggestionResponse> = client.getApi().suggestion(token, request)
        suggestCall.enqueue(object : Callback<SuggestionResponse> {
            override fun onResponse(call: Call<SuggestionResponse>, response: Response<SuggestionResponse>) {
                getNavigator()?.hideSpin()
                val suggestResponse = response.body()
                val statusCode = suggestResponse?.statuscode
                val data = suggestResponse?.data
                if (statusCode == 200) {
                    getNavigator()?.sendSuggestions(data)
                } else {
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }

            override fun onFailure(call: Call<SuggestionResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }
        })
    }

}

interface NotificationNavigator {

    fun goToSuggestPage()

    fun sendSuggestions(player: List<SuggestData>?)

    fun showSnackBarMessage(msg: String)

    fun showSpin()

    fun hideSpin()

    fun goToU19()

    fun goToU21()

    fun goToU23()
}

@Module
class NotificationModule {

    @Provides
    fun providesNotificationViewModel(application: Application): NotificationViewModel {
        return NotificationViewModel(application)
    }
}
