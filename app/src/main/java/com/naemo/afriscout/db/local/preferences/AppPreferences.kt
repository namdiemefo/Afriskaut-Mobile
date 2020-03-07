package com.naemo.afriscout.db.local.preferences

import android.content.Context
import android.util.Log
import com.naemo.afriscout.api.models.login.User
import com.naemo.afriscout.api.models.profile.RetrieveImageResponse
import javax.inject.Inject

class AppPreferences(context: Context) :
    PreferencesHelper {

    private val SHARED_PREF_KEY_NAME = "afriscout_shared_pref"
    var context: Context? = null
        @Inject set



    init {
        this.context = context
    }

    override fun saveUser(user: User) {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        editor?.putString("email", user.email)
        editor?.putString("firstName", user.firstName)
        editor?.putBoolean("isVerified",user.isVerified)
        editor?.putString("token", user.jwt_token)
        editor?.putString("lastName", user.lastName)
        editor?.putString("role", user.role)
        editor?.putString("status", user.status)
        editor?.putString("subscription", user.subscription)
        Log.d("login", user.firstName)

        editor?.apply()
    }

    override fun getUser(): User {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        val firstName = preferences?.getString("email", null)
        Log.d("register", firstName!!)
        return User(
            preferences.getString("email", null),
            preferences.getString("firstName", null),
            preferences.getBoolean("isVerified", false),
            preferences.getString("token", null),
            preferences.getString("lastName", null),
            preferences.getString("role", null),
            preferences.getString("status", null),
            preferences.getString("subscription", null)
        )


    }

    override fun saveImage(img: RetrieveImageResponse) {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        editor?.putString("data", img.data)
        editor?.putString("message", img.message)
        editor?.putInt("code", img.statuscode)
        Log.d("image", img.data)
        editor?.apply()

    }


}

interface PreferencesHelper {

    fun saveUser(user: User)

    fun getUser(): User

    fun saveImage(img: RetrieveImageResponse)

    //fun getImage(): RetrieveImageResponse
}