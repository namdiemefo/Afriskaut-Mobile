package com.naemo.afriscout.views.account.login

import android.app.Application
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.login.LoginRequest
import com.naemo.afriscout.api.models.login.LoginResponse
import com.naemo.afriscout.db.local.AppPreferences
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class LoginViewModel(application: Application) : BaseViewModel<LoginNavigator>(application) {


    var email = ObservableField("")
    var password = ObservableField("")
    var client = Client()
    @Inject set

    var appPreferences = AppPreferences(application)
    @Inject set

    var appUtils = AppUtils()
    @Inject set

    init {
        loadLogin()
    }

   fun loadLogin() {
        val mEmail = email.get().toString()
        val mPassword = password.get().toString()

        if (TextUtils.isEmpty(mEmail)) {
            getNavigator()?.showSnackBar("Enter email")
            return
        } else if (TextUtils.isEmpty(mPassword)) {
            getNavigator()?.showSnackBar("Enter password")
        } else if (appUtils.isEmailValid(mEmail)) {
            if (appUtils.isPasswordValid(mPassword)) {
                getNavigator()?.showSpin()
                val login = LoginRequest(mEmail, mPassword)
                val loginResponseCall: Call<LoginResponse> = client.getApi().login(login)
                loginResponseCall.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse: LoginResponse? = response.body()
                        getNavigator()?.hideSpin()
                        val resCode = loginResponse?.statuscode
                        if (resCode == 200) {
                            appPreferences.saveUser(loginResponse.data.user)
                            getNavigator()?.goToMain()
                        } else {
                            val msg = loginResponse?.message
                           // getNavigator()?.showToast(msg!!)
                            getNavigator()?.showSnackBar(msg!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        getNavigator()?.hideSpin()
                        if (t is IOException) {
                            call.cancel()
                            getNavigator()?.showSnackBar("server error")
                        }
                    }
                })
            } else {
                getNavigator()?.showSnackBar("password must be more than 6 characters")
            }
        } else {
            getNavigator()?.showSnackBar("Invalid email")
        }

    }

}

interface LoginNavigator {

    fun sendLogin()

    fun showToast(msg: String)

    fun showSpin()

    fun hideSpin()

    fun goToMain()

    fun goToForgot()

    fun showSnackBar(msg: String)

    fun hideKeyboard()
}

@Module
class LoginModule {

    @Provides
    fun providesLoginViewModel(application: Application): LoginViewModel {
        return LoginViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_login
    }

}