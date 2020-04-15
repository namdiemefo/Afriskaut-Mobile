package com.naemo.afriscout.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.naemo.afriscout.R

class AppUtils() {

    private var mContext: Context? = null
    lateinit var dialog: Dialog
    //val url: String = "https://www.google.com"

    constructor(context: Context): this() {
        this.mContext = context
    }

    fun isEmailValid(email: String): Boolean {
        val reg =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        return email.matches(reg.toRegex())
    }

    fun isPasswordValid(password: String?): Boolean {
        return password != null && password.length >= 6
    }

    fun bothPasswordValid(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun showActivityToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(context: Context, view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show()
    }

    fun showDialog(context: Context) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun cancelDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkIfCountry(map: Map<Int, String>?): MutableMap<Int, String>? {
        val kk = mutableMapOf<Int, String>()
        val countries = arrayListOf("Algeria",
            "Algeria",
            "Angola",
            "Benin",
            "Botswana",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cameroon",
            "Central African Republic",
            "Chad",
            "Comoros",
            "Democratic Republic of the Congo",
            "Republic of the Congo",
            "Cote d'Ivoire",
            "Djibouti",
            "Egypt",
            "Equatorial Guinea",
            "Eritrea",
            "Eswatini",
            "Ethiopia",
            "Gabon",
            "Gambia",
            "Ghana",
            "Guinea",
            "Guinea-Bissau",
            "Kenya",
            "Lesotho",
            "Liberia",
            "Libya",
            "Madagascar",
            "Malawi",
            "Mali",
            "Mauritania",
            "Mauritius",
            "Morocco",
            "Mozambique",
            "Namibia",
            "Niger",
            "Nigeria",
            "Rwanda",
            "Sao Tome and Principe",
            "Senegal",
            "Seychelles",
            "Sierra Leone",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Sudan",
            "Tanzania",
            "Togo",
            "Tunisia",
            "Uganda",
            "Zambia",
            "Zimbabwe")

        map?.let {
            for ((k, v) in it) {
                if (countries.contains(v)) {
                    kk[k] = v
                }
            }
        }

        return kk

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkIfClub(map: Map<Int, String>?): MutableMap<Int, String>? {
        val kk = mutableMapOf<Int, String>()
        val countries = arrayListOf("Algeria",
            "Algeria",
            "Angola",
            "Benin",
            "Botswana",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cameroon",
            "Central African Republic",
            "Chad",
            "Comoros",
            "Democratic Republic of the Congo",
            "Republic of the Congo",
            "Cote d'Ivoire",
            "Djibouti",
            "Egypt",
            "Equatorial Guinea",
            "Eritrea",
            "Eswatini",
            "Ethiopia",
            "Gabon",
            "Gambia",
            "Ghana",
            "Guinea",
            "Guinea-Bissau",
            "Kenya",
            "Lesotho",
            "Liberia",
            "Libya",
            "Madagascar",
            "Malawi",
            "Mali",
            "Mauritania",
            "Mauritius",
            "Morocco",
            "Mozambique",
            "Namibia",
            "Niger",
            "Nigeria",
            "Rwanda",
            "Sao Tome and Principe",
            "Senegal",
            "Seychelles",
            "Sierra Leone",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Sudan",
            "Tanzania",
            "Togo",
            "Tunisia",
            "Uganda",
            "Zambia",
            "Zimbabwe")

        map?.let { kk.putAll(it) }
        map?.let {
            for ((k,v) in it) {
                if (countries.contains(v)) {
                    kk.remove(k, v)
                    Log.d("stuff14", kk.toString())
                }
            }
        }

        Log.d("stuff15", kk.toString())
        return kk
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkIfsCountry(map: Map<String, String>?): MutableMap<String, String>? {
        val kk = mutableMapOf<String, String>()
        val countries = arrayListOf("Algeria",
            "Algeria",
            "Angola",
            "Benin",
            "Botswana",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cameroon",
            "Central African Republic",
            "Chad",
            "Comoros",
            "Democratic Republic of the Congo",
            "Republic of the Congo",
            "Cote d'Ivoire",
            "Djibouti",
            "Egypt",
            "Equatorial Guinea",
            "Eritrea",
            "Eswatini",
            "Ethiopia",
            "Gabon",
            "Gambia",
            "Ghana",
            "Guinea",
            "Guinea-Bissau",
            "Kenya",
            "Lesotho",
            "Liberia",
            "Libya",
            "Madagascar",
            "Malawi",
            "Mali",
            "Mauritania",
            "Mauritius",
            "Morocco",
            "Mozambique",
            "Namibia",
            "Niger",
            "Nigeria",
            "Rwanda",
            "Sao Tome and Principe",
            "Senegal",
            "Seychelles",
            "Sierra Leone",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Sudan",
            "Tanzania",
            "Togo",
            "Tunisia",
            "Uganda",
            "Zambia",
            "Zimbabwe")

        map?.let {
            for ((k, v) in it) {
                if (countries.contains(k)) {
                    kk[k] = v
                }
            }
        }

        return kk

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkIfsClub(map: Map<String, String>?): MutableMap<String, String>? {
        val kk = mutableMapOf<String, String>()
        val countries = arrayListOf("Algeria",
            "Algeria",
            "Angola",
            "Benin",
            "Botswana",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cameroon",
            "Central African Republic",
            "Chad",
            "Comoros",
            "Democratic Republic of the Congo",
            "Republic of the Congo",
            "Cote d'Ivoire",
            "Djibouti",
            "Egypt",
            "Equatorial Guinea",
            "Eritrea",
            "Eswatini",
            "Ethiopia",
            "Gabon",
            "Gambia",
            "Ghana",
            "Guinea",
            "Guinea-Bissau",
            "Kenya",
            "Lesotho",
            "Liberia",
            "Libya",
            "Madagascar",
            "Malawi",
            "Mali",
            "Mauritania",
            "Mauritius",
            "Morocco",
            "Mozambique",
            "Namibia",
            "Niger",
            "Nigeria",
            "Rwanda",
            "Sao Tome and Principe",
            "Senegal",
            "Seychelles",
            "Sierra Leone",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Sudan",
            "Tanzania",
            "Togo",
            "Tunisia",
            "Uganda",
            "Zambia",
            "Zimbabwe")

        map?.let { kk.putAll(it) }
        map?.let {
            for ((k,v) in it) {
                if (countries.contains(k)) {
                    kk.remove(k, v)
                    Log.d("stuff14", kk.toString())
                }
            }
        }

        return kk

    }


}

