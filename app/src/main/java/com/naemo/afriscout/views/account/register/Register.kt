package com.naemo.afriscout.views.account.register

import android.app.Application
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.api.models.register.RegisterRequest
import com.naemo.afriscout.api.models.register.RegisterResponse
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

class RegisterViewModel(application: Application) : BaseViewModel<RegisterNavigator>(application) {

    private var mRoles: String? = null
    var firstName = ObservableField("")
    var lastName = ObservableField("")
    var email = ObservableField("")
    var password = ObservableField("")
    var confirmPassword = ObservableField("")

    var appUtils = AppUtils()
        @Inject set

    var client = Client()
        @Inject set

    init {
        loadRegister(roles = String())
    }

    fun loadRegister(roles: String) {
        val mFirstName = firstName.get().toString()
        val mLastName = lastName.get().toString()
        val mEmail = email.get().toString()
        val mPassword = password.get().toString()
        val mConfirmPassword = confirmPassword.get().toString()
        mRoles = roles

        if (TextUtils.isEmpty(mFirstName)) {
            getNavigator()?.showSnackBar("Enter first name ")
        } else if (TextUtils.isEmpty(mLastName)) {
            getNavigator()?.showSnackBar("Enter last name")
        } else if (TextUtils.isEmpty(mEmail)) {
            getNavigator()?.showSnackBar("Enter email ")
        } else if (TextUtils.isEmpty(mPassword)) {
            getNavigator()?.showSnackBar("Enter password")
        } else if (TextUtils.isEmpty(mConfirmPassword)) {
            getNavigator()?.showSnackBar("confirm password")
        } else if (TextUtils.isEmpty(mRoles)) {
            getNavigator()?.showSnackBar("Enter role in sports")
        }  else if (appUtils.isEmailValid(mEmail)) {
            if (appUtils.isPasswordValid(mPassword)) {
                 if (appUtils.bothPasswordValid(mPassword, mConfirmPassword)) {
                     getNavigator()?.showSpin()
                     val register = RegisterRequest(mEmail, mFirstName, mLastName, mPassword, mRoles!!)
                     val registerResponseCall: Call<RegisterResponse> = client.getApi().register(register)
                     registerResponseCall.enqueue(object : Callback<RegisterResponse> {
                         override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                             getNavigator()?.hideSpin()
                             if (t is IOException) {
                                 call.cancel()
                                 getNavigator()?.showSnackBar("server error")
                             }
                         }

                         override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                             val registerResponse: RegisterResponse? = response.body()
                             getNavigator()?.hideSpin()
                             val resCode = registerResponse?.statuscode
                             val msg = registerResponse?.message
                             if (resCode == 201) {
                                 getNavigator()?.goToLogin()
                                 getNavigator()?.showSnackBar(msg!!)
                             } else {
                                 getNavigator()?.showSnackBar(msg!!)
                             }

                         }

                     })
                } else {
                     getNavigator()?.showSnackBar("Passwords do not match")
                 }

            } else {
                getNavigator()?.showSnackBar("Password must be more than 6")
            }
        } else {
            getNavigator()?.showSnackBar("Enter a valid email")
        }
    }


}

interface RegisterNavigator {

    fun sendRegister()

    fun showToast(msg: String)

    fun showSnackBar(msg: String)

    fun showSpin()

    fun hideSpin()

    fun goToLogin()

    fun goToPp()

    fun goToTos()
}

@Module
class RegisterModule {

    @Provides
    fun providesRegisterViewModel(application: Application): RegisterViewModel {
        return RegisterViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_register
    }

}