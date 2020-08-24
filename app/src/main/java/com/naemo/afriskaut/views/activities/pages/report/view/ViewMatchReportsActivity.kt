package com.naemo.afriskaut.views.activities.pages.report.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.report.ReportData
import com.naemo.afriskaut.databinding.ActivityViewMatchReportsBinding
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.report.create.CreateReportActivity
import com.naemo.afriskaut.views.adapters.ReportAdapter
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_match_reports.*
import javax.inject.Inject

class ViewMatchReportsActivity : BaseActivity<ActivityViewMatchReportsBinding, ViewMatchReportsViewModel>(), ViewMatchReportsNavigator, ReportAdapter.ItemClicklistener {

    var viewMatchReportsViewModel: ViewMatchReportsViewModel? = null
        @Inject set

    var mBinder: ActivityViewMatchReportsBinding? = null

    var appUtils: AppUtils? = null
        @Inject set

    var mLayoutId = R.layout.activity_view_match_reports
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()
        initViews()
    }

    private fun initViews() {
        val intent = intent
        val from = intent.getIntExtra("from", 0)

        if (from == 1) {
            getViewModel()?.viewAllReports()
        } else {
            val id = intent.getStringExtra("id")
            id?.let { getViewModel()?.viewReports(it)  }
        }

    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = viewMatchReportsViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ViewMatchReportsViewModel? {
        return viewMatchReportsViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun displayReports(reports: List<ReportData>?) {
        if (reports.isNullOrEmpty()) {
            showSnackBarMessage("Create a report from radar page")
        }
        val adapter = reports?.let { ReportAdapter(this, it, this) }
        report_recycler.adapter = adapter
        report_recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(this, view_report_frame, msg)
    }

    override fun onItemClicked(report: ReportData) {
        // go to created report
        val intent = Intent(this, CreateReportActivity::class.java)
        intent.putExtra("report", report)
        intent.putExtra("from", 1)
        startActivity(intent)
    }
}
