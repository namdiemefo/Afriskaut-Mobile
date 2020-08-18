package com.naemo.afriskaut.views.activities.pages.report.create

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.report.ReportData
import com.naemo.afriskaut.databinding.ActivityCreateReportBinding
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_create_report.*
import kotlinx.android.synthetic.main.pop_up.*
import kotlinx.android.synthetic.main.remark_popup.*
import javax.inject.Inject


class CreateReportActivity : BaseActivity<ActivityCreateReportBinding, CreateReportViewModel>(), CreateReportNavigator {

    var createReportViewModel: CreateReportViewModel? = null
        @Inject set

    var mBinder: ActivityCreateReportBinding? = null

    var appUtils: AppUtils? = null
        @Inject set

    var mLayoutId = R.layout.activity_create_report
        @Inject set

    var report: String? = null
    var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()
        initViews()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = createReportViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    private fun initViews() {
        val intent = intent
        val from = intent.getIntExtra("from", 0)
        if (from == 1) {
            opponent.visibility = View.GONE
            vs.visibility = View.GONE
            save.setImageResource(R.drawable.ic_delete)
            val report = intent.getParcelableExtra<ReportData>("report")
            id = report?.id
            report?.let {
                mBinder?.playerName?.text = report.matchname.toString()
                mBinder?.passScore?.text = report.shortpass.toString()
                mBinder?.bcScore?.text = it.ballcontrol.toString()
                mBinder?.composureScore?.text = it.composure.toString()
                mBinder?.dmScore?.text = it.decisionmaking.toString()
                mBinder?.finishingScore?.text = it.finishing.toString()
                mBinder?.headingScore?.text = it.heading.toString()
                mBinder?.techniqueScore?.text = it.technique.toString()
                mBinder?.ftScore?.text = it.firsttouch.toString()
                mBinder?.lpScore?.text = it.longpass.toString()
                mBinder?.positioningScore?.text = it.positioning.toString()
                mBinder?.ssScore?.text = it.shotstopping.toString()
                mBinder?.pressingScore?.text = it.pressing.toString()
            }
            mBinder?.save?.setOnClickListener {
                id?.let { it1 -> getViewModel()?.deleteReport(it1) }
            }


           // getViewModel()?.displayReport(report)
        } else {
            val name = intent.getStringExtra("name")
            id = intent.getStringExtra("id")

            player_name.text = name

            remark_btn.setOnClickListener {
                showRemarkPopUp()
            }

            passing.setOnClickListener {
                showPopUp(pass_score)
            }

            composure.setOnClickListener {
                showPopUp(composure_score)
            }

            heading.setOnClickListener {
                showPopUp(heading_score)
            }

            first_touch.setOnClickListener {
                showPopUp(ft_score)
            }

            technique.setOnClickListener {
                showPopUp(technique_score)
            }

            finishing.setOnClickListener {
                showPopUp(finishing_score)
            }

            ball_control.setOnClickListener {
                showPopUp(bc_score)
            }

            decision_making.setOnClickListener {
                showPopUp(dm_score)
            }

            shot_stopping.setOnClickListener {
                showPopUp(ss_score)
            }

            long_pass.setOnClickListener {
                showPopUp(lp_score)
            }

            positioning.setOnClickListener {
                showPopUp(positioning_score)
            }

            pressing.setOnClickListener {
                showPopUp(pressing_score)
            }
        }




    }

    private fun showRemarkPopUp() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.remark_popup, popup_remark)

        val btn = view.findViewById<Button>(R.id.submit_remark_btn)
        val remark = view.findViewById<EditText>(R.id.remark)

        val focusable = true
        val pw = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, focusable)
        pw.showAtLocation(container, Gravity.CENTER, 0, 0)

        btn.setOnClickListener {
            // add remark
            report = remark.text.toString()
            pw.dismiss()
        }

        view.setOnTouchListener { _, _ ->
            //Close the window when clicked
            pw.dismiss()
            true
        }
    }

    private fun showPopUp(passScore: TextView) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.pop_up, popup)

        val up = view.findViewById<ImageButton>(R.id.increment)
        val down = view.findViewById<ImageButton>(R.id.decrement)
        val focusable = true


        val pw = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, focusable)
        pw.showAtLocation(container, Gravity.CENTER, 0, 0)



        up.setOnClickListener {
            val score = view.findViewById<TextView>(R.id.score)
            addOne(score, passScore)

        }
        down.setOnClickListener {
            val score = view.findViewById<TextView>(R.id.score)
            minusOne(score, passScore)
        }



        view.setOnTouchListener { _, _ ->
            //Close the window when clicked
            pw.dismiss()
            true
        }


    }


    var add = 0

    private fun addOne(score: TextView, passScore: TextView) {
        if (score.text.toString().toInt() < 10) {
            add++
            score.text = add.toString()
            passScore.text  = add.toString()
            passScore.setTextColor(resources.getColor(R.color.colorPlayerImageBackground))

        }
    }

    private fun minusOne(score: TextView, passScore: TextView) {
        if (score.text.toString().toInt() > 0) {
            add--
            score.text = add.toString()
            passScore.text = add.toString()
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): CreateReportViewModel? {
        return createReportViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun save() {
       // val intent = intent
       // val id = intent.getStringExtra("id")
        val matchName = mBinder?.playerName?.text.toString() + " vs " + mBinder?.opponent?.text?.toString()
        val shortPass = mBinder?.passScore?.text.toString().toInt()
        val composure = mBinder?.composureScore?.text.toString().toInt()
        val heading = mBinder?.headingScore?.text.toString().toInt()
        val firstTouch = mBinder?.ftScore?.text.toString().toInt()
        val technique = mBinder?.techniqueScore?.text.toString().toInt()
        val finishing = mBinder?.finishingScore?.text.toString().toInt()
        val ballControl= mBinder?.bcScore?.text.toString().toInt()
        val decisionMaking = mBinder?.dmScore?.text.toString().toInt()
        val shotStopping = mBinder?.ssScore?.text.toString().toInt()
        val longPass = mBinder?.lpScore?.text.toString().toInt()
        val positioning = positioning_score.text.toString().toInt()
        val pressing = mBinder?.pressingScore?.text.toString().toInt()
        getViewModel()?.saveReport(matchName, id, report, shortPass, composure, firstTouch, heading, technique, finishing, ballControl, decisionMaking, shotStopping, longPass, positioning, pressing)

    }

    override fun showSpin() {
        appUtils?.showDialog(this)
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(this, container, msg)
    }
}
