package com.naemo.afriskaut.views.activities.account.forgotpassword

import android.app.Application
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.forgot.ForgotRequest
import com.naemo.afriskaut.api.models.forgot.ForgotResponse
import com.naemo.afriskaut.network.Client
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ForgotViewModel(application: Application) : BaseViewModel<ForgotNavigator>(application) {

    var email = ObservableField("")
    var client = Client()
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    init {
        loadToken()
    }

    fun loadToken() {
        val mEmail = email.get().toString()

        if (TextUtils.isEmpty(mEmail)) {
            getNavigator()?.showSnackBar("Enter registered e-mail address")
        } else if (appUtils.isEmailValid(mEmail)) {
            getNavigator()?.showSpin()
            val forgot = ForgotRequest(mEmail)
            val forgotResponseCall: Call<ForgotResponse> = client.getApi().reset(forgot)
            forgotResponseCall.enqueue(object : Callback<ForgotResponse> {
                override fun onFailure(call: Call<ForgotResponse>, t: Throwable) {
                    getNavigator()?.hideSpin()
                    if (t is IOException) {
                        call.cancel()
                        getNavigator()?.showSnackBar("server error")
                    }
                }

                override fun onResponse(call: Call<ForgotResponse>, response: Response<ForgotResponse>) {
                    val forgotResponse: ForgotResponse? = response.body()
                    getNavigator()?.hideSpin()
                    val resCode = forgotResponse?.response?.statuscode
                    val msg = forgotResponse?.response?.message
                    if (resCode == 200) {
                        getNavigator()?.showSnackBar(msg!!)
                        email.set("")
                    } else {
                        getNavigator()?.showSnackBar(msg!!)
                    }
                }

            })
        } else {
            getNavigator()?.showSnackBar("Enter a valid email")
        }
    }
}

interface ForgotNavigator {

    fun sendToken()

    fun showSnackBar(msg: String)

    fun goToLogin()

    fun showSpin()

    fun hideSpin()
}

@Module
class ForgotModule {

    @Provides
    fun providesForgotViewModel(application: Application): ForgotViewModel {
        return ForgotViewModel(
            application
        )
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_forgot
    }
}