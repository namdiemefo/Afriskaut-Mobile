package com.naemo.afriscout.views.account.forgotpassword

import android.app.Application
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
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

    private fun loadToken() {
        val mEmail = email.get().toString()

        if (TextUtils.isEmpty(mEmail)) {
            getNavigator()?.showSnackBar("Enter registered e-mail address")
        } else if (appUtils.isEmailValid(mEmail)) {
            getNavigator()?.showSpin()

        }
    }
}

interface ForgotNavigator {

    fun sendToken()

    fun showSnackBar(msg: String)

    fun showSpin()
}

@Module
class ForgotModule {

    @Provides
    fun providesForgotViewModel(application: Application): ForgotViewModel {
        return ForgotViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_forgot
    }
}