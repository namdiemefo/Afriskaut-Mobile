package com.naemo.afriskaut.views.activities.pages.report.create

import android.app.Application
import android.util.Log
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.report.CreateReportRequest
import com.naemo.afriskaut.api.models.report.CreateReportResponse
import com.naemo.afriskaut.api.models.report.DeleteReportRequest
import com.naemo.afriskaut.db.local.preferences.AppPreferences
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

class CreateReportViewModel(application: Application): BaseViewModel<CreateReportNavigator>(application) {


    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set


    fun deleteReport(id: String) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = DeleteReportRequest(id)

        val deleteCall: Call<CreateReportResponse> = client.getApi().deleteReport(token, request)
        deleteCall.enqueue(object : Callback<CreateReportResponse> {
            override fun onResponse(call: Call<CreateReportResponse>, response: Response<CreateReportResponse>) {
                getNavigator()?.hideSpin()
                val body = response.body()
                val statusCode = body?.statuscode
                val msg = body?.message

                if (statusCode == 200) {
                    msg?.let { getNavigator()?.showSnackBarMessage(it) }
                } else {
                    msg?.let { getNavigator()?.showSnackBarMessage(it) }
                }
            }

            override fun onFailure(call: Call<CreateReportResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }
        })

    }

    fun saveReport(
        matchName: String,
        id: String?,
        report: String?,
        shortPass: Int,
        composure: Int,
        firstTouch: Int,
        heading: Int,
        technique: Int,
        finishing: Int,
        ballControl: Int,
        decisionMaking: Int,
        shotStopping: Int,
        longPass: Int,
        positioning: Int,
        pressing: Int
    ) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = CreateReportRequest(ballControl, composure, decisionMaking, finishing, firstTouch, heading, longPass, matchName, id, positioning, report, shortPass, shotStopping, technique, pressing)

        val reportCall: Call<CreateReportResponse> = client.getApi().createReport(token, request)
        reportCall.enqueue(object : Callback<CreateReportResponse> {
            override fun onResponse(call: Call<CreateReportResponse>, response: Response<CreateReportResponse>) {
                getNavigator()?.hideSpin()
                val body = response.body()
                val statusCode = body?.statuscode
                val msg = body?.message
                if (statusCode == 200) {
                    msg?.let { getNavigator()?.showSnackBarMessage(it) }
                    getNavigator()?.goBack()
                } else {
                    msg?.let { getNavigator()?.showSnackBarMessage(it) }
                }
            }

            override fun onFailure(call: Call<CreateReportResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }
        })
    }

}

interface CreateReportNavigator {

    fun goBack()

    fun save()

    fun showSpin()

    fun hideSpin()

    fun showSnackBarMessage(msg: String)

}

@Module
class CreateReportModule {
    @Provides
    fun providesCreateReportViewModel(application: Application): CreateReportViewModel {
        return CreateReportViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_create_report
    }
}