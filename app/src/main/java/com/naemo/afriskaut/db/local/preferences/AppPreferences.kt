package com.naemo.afriskaut.db.local.preferences

import android.content.Context
import com.naemo.afriskaut.api.models.login.User
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

        editor?.apply()
    }

    override fun getUser(): User {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        return User(
            preferences?.getString("email", null),
            preferences?.getString("firstName", null),
            preferences?.getBoolean("isVerified", false),
            preferences?.getString("token", null),
            preferences?.getString("lastName", null),
            preferences?.getString("role", null),
            preferences?.getString("status", null),
            preferences?.getString("subscription", null)
        )


    }

    override fun logout() {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        editor?.clear()
        editor?.apply()
    }


}

interface PreferencesHelper {

    fun saveUser(user: User)

    fun getUser(): User

    fun logout()
}