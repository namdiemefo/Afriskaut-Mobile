package com.naemo.afriskaut.views.fragments.player.stats

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.search.Stats
import com.naemo.afriskaut.databinding.FragmentStatsBinding
import com.naemo.afriskaut.views.activities.pages.playerstats.FragmentContainerNavigator
import com.naemo.afriskaut.views.base.BaseFragment
import com.naemo.afriskaut.views.fragments.player.decidestats.DecideStatsArgs
import kotlinx.android.synthetic.main.fragment_stats.*
import java.util.*
import javax.inject.Inject


class StatsFragment : BaseFragment<FragmentStatsBinding, StatsViewModel>(), StatsNavigator {

    var statsViewModel: StatsViewModel? = null
        @Inject set

    private lateinit var fragmentNavigator: FragmentContainerNavigator;

    private var mLayoutId = R.layout.fragment_stats

    var mBinder: FragmentStatsBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigator = context as FragmentContainerNavigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doBinding()
        initViews()
    }

    private fun initViews() {
        val args by navArgs<StatsFragmentArgs>()
        val stats = args.stats
        val statss = stats.toList()
        Log.d("stats", stats.toString())
       // val barEntrie: ArrayList<ArrayList<BarEntry>> = ArrayList()
        val values = arrayListOf<String>()
        val offOrgEntry: ArrayList<BarEntry> = ArrayList()
        val offTransEntry: ArrayList<BarEntry> = ArrayList()
        val defOrgEntry: ArrayList<BarEntry> = ArrayList()
        val defTransEntry: ArrayList<BarEntry> = ArrayList()



        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setMaxVisibleValueCount(50)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(true)

        val groupSpace = 0.1f
        val barSpace = 0.02f
        val barWidth = 0.4f

        Log.d("statss", statss.toString())
        statss.let {
            for (i in statss) {
               // val barEntries: ArrayList<BarEntry> = ArrayList()


                values.add(i.leagueName + "/" + i.seasonName)

                val offOrg = i.offensiveOrganization?.toFloat()
                Log.d("statsoff", offOrg.toString())
                val offTrans = i.offensiveTransition?.toFloat()
                val defTrans = i.defensiveTransition?.toFloat()
                val defOrg = i.defensiveOrganization?.toFloat()
                offOrgEntry.add(BarEntry(1f, offOrg!!))
                offTransEntry.add(BarEntry(1f + barWidth, offTrans!!))
                defOrgEntry.add(BarEntry(1f + 2 * barWidth , defOrg!!))
                defTransEntry.add(BarEntry(1f + 3 * barWidth, defTrans!!))
            }
        }

        barChart.axisRight.isEnabled = false

        val offOrgDataSet = BarDataSet(offOrgEntry, "Offensive Organization")
        offOrgDataSet.color = R.color.colorPlayerImageBackground

        val offTransDataSet = BarDataSet(offTransEntry, "Offensive Transition")
        offTransDataSet.color = R.color.colorCreateAccBtn

        val defTransDataSet = BarDataSet(defTransEntry, "Defensive Transition")
        defTransDataSet.color = R.color.colorHomeText

        val defOrgDataSet = BarDataSet(defOrgEntry, "Defensive Organization")
        defOrgDataSet.color = R.color.colorLoginBtn



        val data = BarData(offOrgDataSet, offTransDataSet, defTransDataSet, defOrgDataSet)
        data.barWidth = barWidth
        barChart.data = data
        //barChart.groupBars(1f, groupSpace, barSpace)

        Log.d("valuessss", values.toString())
        //val xValues = values.toTypedArray()
//        val xAxis: XAxis = barChart.xAxis
//        xAxis.valueFormatter = MyIndexValueFormatter(values)
//        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
//        xAxis.setCenterAxisLabels(true)
//        xAxis.axisMinimum = 1f


    }

    class MyIndexValueFormatter(values: ArrayList<String>?) : ValueFormatter() {
        var mValues : ArrayList<String>? = values

        override fun getFormattedValue(value: Float): String {
            Log.d("values", value.toString())
            Log.d("value", mValues.toString())
            return mValues?.get(value.toInt()).toString()
        }

    }


    private val COLORFUL_COLORS = arrayListOf(
        Color.rgb(193, 37, 82),
        Color.rgb(255, 102, 0),
        Color.rgb(245, 199, 0),
        Color.rgb(106, 150, 31),
        Color.rgb(179, 100, 53)
    )

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = statsViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): StatsViewModel? {
        return statsViewModel
    }

    override fun goBack() {
        val args by navArgs<DecideStatsArgs>()
        val player = args.player
        fragmentNavigator.navigateToPickStatsPage(player)
    }
}



