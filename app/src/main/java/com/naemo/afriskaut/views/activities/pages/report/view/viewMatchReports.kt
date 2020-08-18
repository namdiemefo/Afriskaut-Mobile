package com.naemo.afriskaut.views.activities.pages.report.view

import android.app.Application
import android.util.Log
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.report.ReportData
import com.naemo.afriskaut.api.models.report.ViewReportRequest
import com.naemo.afriskaut.api.models.report.ViewReportResponse
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

class ViewMatchReportsViewModel(application: Application): BaseViewModel<ViewMatchReportsNavigator>(application) {

    var appUtils: AppUtils? = null
        @Inject set

    var appPreferences = AppPreferences(application)
        @Inject set

    var client = Client()
        @Inject set

    fun viewReports(id: String) {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"
        val request = ViewReportRequest(id)

        val viewReportsCall: Call<ViewReportResponse> = client.getApi().viewReport(token, request)
        viewReportsCall.enqueue(object : Callback<ViewReportResponse> {
            override fun onResponse(call: Call<ViewReportResponse>, response: Response<ViewReportResponse>) {
                getNavigator()?.hideSpin()
                val body = response.body()
                val statusCode = body?.statuscode
                val data = body?.data
                val msg = body?.message
                if (statusCode == 200) {
                    getNavigator()?.displayReports(data)
                } else {
                    msg?.let { getNavigator()?.showSnackBarMessage(it) }
                }
            }

            override fun onFailure(call: Call<ViewReportResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }
        })
    }

    fun viewAllReports() {
        getNavigator()?.showSpin()
        val user = appPreferences.getUser()
        val userToken = user.jwt_token
        val token = "Bearer $userToken"

        val viewAllReportsCall: Call<ViewReportResponse> = client.getApi().viewAllReport(token)
        viewAllReportsCall.enqueue(object : Callback<ViewReportResponse> {
            override fun onResponse(call: Call<ViewReportResponse>, response: Response<ViewReportResponse>) {
                getNavigator()?.hideSpin()
                val body = response.body()
                val statusCode = body?.statuscode
                val msg = body?.message
                val reports = body?.data

                if (statusCode == 200) {
                    getNavigator()?.displayReports(reports)
                } else {
                    msg?.let { getNavigator()?.showSnackBarMessage(it) }
                }
            }

            override fun onFailure(call: Call<ViewReportResponse>, t: Throwable) {
                getNavigator()?.hideSpin()
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.showSnackBarMessage("server error, try again later")
                }
            }
        })
    }


}

interface ViewMatchReportsNavigator {

    fun displayReports(reports: List<ReportData>?)

    fun showSpin()

    fun hideSpin()

    fun showSnackBarMessage(msg: String)
}

@Module
class ViewMatchReportsModule {
    @Provides
    fun providesViewMatchReportsViewModel(application: Application): ViewMatchReportsViewModel {
        return ViewMatchReportsViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_view_match_reports
    }
}