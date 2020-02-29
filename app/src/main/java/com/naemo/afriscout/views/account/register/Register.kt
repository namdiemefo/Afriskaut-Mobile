package com.naemo.afriscout.views.account.register

import android.app.Application
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
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

    init {
        loadRegister(application, roles = String())
    }

    private fun loadRegister(application: Application, roles: String) {
        val mFirstName = firstName.get().toString()
        val mLastNmae = lastName.get().toString()
        val mEmail = email.get().toString()
        val mPassword = password.get().toString()
        val mConfirmPassword = confirmPassword.get().toString()
        mRoles = roles

        if (TextUtils.isEmpty(mFirstName) || TextUtils.isEmpty(mLastNmae) || TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(mConfirmPassword)) {

        }
    }


}

interface RegisterNavigator {

    fun sendRegister()

    fun showToast(msg: String)

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