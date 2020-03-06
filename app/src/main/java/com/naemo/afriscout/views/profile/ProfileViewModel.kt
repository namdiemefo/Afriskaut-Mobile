package com.naemo.afriscout.views.profile

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.databinding.ObservableField
import com.naemo.afriscout.R
import com.naemo.afriscout.db.local.AppPreferences
import com.naemo.afriscout.network.Client
import com.naemo.afriscout.utils.AppUtils
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

class ProfileViewModel(application: Application) : BaseViewModel<ProfileNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    var fullName = ObservableField("")
    var role = ObservableField("")


    fun setUpProfile() {
        val user = appPreferences.getUser()
        val name = user.firstName.plus(" ").plus(user.lastName)
        Log.d("viewModel", name)
        val profileRole = user.role
        fullName.set(name)
        role.set(profileRole)
    }


}

interface ProfileNavigator {

    fun openGallery()
}

@Module
class ProfileModule {

    @Provides
    fun provideProfileViewModel(application: Application): ProfileViewModel {
        return ProfileViewModel(application)
    }

    @Provides
    @Named("profile")
    fun layoutId(): Int {
          return R.layout.profile_fragment
    }


}
