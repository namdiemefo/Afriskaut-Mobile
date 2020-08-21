package com.naemo.afriskaut.views.fragments.player.stats

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
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

    private lateinit var fragmentNavigator: FragmentContainerNavigator

    private var mLayoutId = R.layout.fragment_stats

    var mBinder: FragmentStatsBinding? = null

    val values = arrayListOf<String>()
    private val dataSets = arrayListOf<BarDataSet>()
    private val barEntries = arrayListOf<Stats>()
    private var barWidth = -1f


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigator = context as FragmentContainerNavigator
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doBinding()
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {

        val args by navArgs<StatsFragmentArgs>()
        val stats = args.stats
        val playerStats = stats.toList()

        drawChart(playerStats.size)
        addHeaders()
        addRows(playerStats)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addRows(playerStats: List<Stats>) {
        playerStats.let {
            for (stats in it) {
                val tr = TableRow(requireContext())
                tr.layoutParams = getLayoutParams()

                tr.addView(getRowsTextView(0, stats.seasonName, requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.leagueName, requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.goals.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.assists.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.appearences.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.passesAccurate.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.dribbles?.success.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.blocks.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.tackles.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                tr.addView(getRowsTextView(0, stats.duels?.won.toString(), requireContext().getColor(R.color.colorCreateAccBtn), Typeface.BOLD, R.drawable.cell_shape))
                stats_table.addView(tr, getTblLayoutParams())
            }
        }

    }

    private fun getRowsTextView(i: Int, s: String?, color: Int, bold: Int, cellShape: Int): View? {
        val tv = TextView(requireContext())
        tv.id = i
        tv.text = s.toString()
        tv.setTextColor(color)
        tv.setPadding(10,10,10,10)
        tv.setTypeface(Typeface.DEFAULT, bold)
        tv.textSize = 7f
        tv.layoutParams = getLayoutParams()
        tv.setBackgroundResource(cellShape)
        return tv
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun addHeaders() {
        val tr = TableRow(requireContext())
        tr.addView(getTextView(0, "S", requireContext().getColor(R.color.colorPlayerBackground), Typeface.ITALIC, R.drawable.cell_shape))
        tr.addView(getTextView(0, "Comp.", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "G", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "A", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "App", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "Pass%", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "Drb", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "Blocks", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "Tackles", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        tr.addView(getTextView(0, "Duels", requireContext().getColor(R.color.colorPlayerBackground), Typeface.BOLD, R.drawable.cell_shape))
        stats_table.addView(tr)
    }

    private fun getTextView(i: Int, s: String, color: Int, bold: Int, cellShape: Int): View? {
        val tv = TextView(requireContext())
        tv.id = i
        tv.text = s
        tv.setTextColor(color)
        tv.setPadding(10,10,10,10)
        tv.setTypeface(Typeface.DEFAULT, bold)
        tv.textSize = 7f
        tv.layoutParams = getLayoutParams()
        tv.setBackgroundResource(cellShape)
        return tv
    }

     private fun getLayoutParams(): TableRow.LayoutParams {
         val params = TableRow.LayoutParams(
             TableRow.LayoutParams.MATCH_PARENT,
             TableRow.LayoutParams.WRAP_CONTENT)
         params.setMargins(1, 1, 1, 1)
         params.weight = 1f
         return params
     }

    private fun getTblLayoutParams(): TableLayout.LayoutParams {
        return TableLayout.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawChart(size: Int) {
        val args by navArgs<StatsFragmentArgs>()
        val stats = args.stats
        val playerStats = stats.toList()
        val offOrgEntry: ArrayList<BarEntry> = ArrayList()
        val offTransEntry: ArrayList<BarEntry> = ArrayList()
        val defOrgEntry: ArrayList<BarEntry> = ArrayList()
        val defTransEntry: ArrayList<BarEntry> = ArrayList()

        barChart.description.isEnabled = false
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setMaxVisibleValueCount(50)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)

        barChart.axisRight.isEnabled = false
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.textColor = requireContext().getColor(R.color.colorWhite)
        barChart.xAxis.textColor = requireContext().getColor(R.color.colorWhite)

        val legend = barChart.legend
        legend.isWordWrapEnabled = true
        legend.textSize = 12f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.form = Legend.LegendForm.CIRCLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            legend.textColor = requireContext().getColor(R.color.colorWhite)
        }

        playerStats.let {
            for ((index,value) in it.withIndex()) {

                values.add(value.leagueName + "/" + value.seasonName)
                barEntries.add(value)
                val offOrg = value.offensiveOrganization?.toFloat()
                val offTrans = value.offensiveTransition?.toFloat()
                val defTrans = value.defensiveTransition?.toFloat()
                val defOrg = value.defensiveOrganization?.toFloat()
                offOrgEntry.add(BarEntry(index.toFloat(), offOrg!!))
                offTransEntry.add(BarEntry(index.toFloat(), offTrans!!))
                defOrgEntry.add(BarEntry(index.toFloat() , defOrg!!))
                defTransEntry.add(BarEntry(index.toFloat(), defTrans!!))
            }
        }

        barChart.axisRight.isEnabled = false

        val xAxis: XAxis = barChart.xAxis
        xAxis.valueFormatter = MyIndexValueFormatter(values)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setCenterAxisLabels(true)
        xAxis.isGranularityEnabled = true

        val offOrgDataSet = BarDataSet(offOrgEntry, "Offensive Organization")
        offOrgDataSet.color = requireContext().getColor(R.color.colorLoginBtn)
        offOrgDataSet.valueTextColor = requireContext().getColor(R.color.colorCreateAccBtn)

        val offTransDataSet = BarDataSet(offTransEntry, "Offensive Transition")
        offTransDataSet.color = requireContext().getColor(R.color.colorPrimaryDark)
        offTransDataSet.valueTextColor = requireContext().getColor(R.color.colorCreateAccBtn)

        val defTransDataSet = BarDataSet(defTransEntry, "Defensive Transition")
        defTransDataSet.color = requireContext().getColor(R.color.colorHomeText)
        defTransDataSet.valueTextColor = requireContext().getColor(R.color.colorCreateAccBtn)

        val defOrgDataSet = BarDataSet(defOrgEntry, "Defensive Organization")
        defOrgDataSet.color = requireContext().getColor(R.color.colorHint)
        defOrgDataSet.valueTextColor = requireContext().getColor(R.color.colorCreateAccBtn)

        dataSets.add(offOrgDataSet)
        dataSets.add(offTransDataSet)
        dataSets.add(defTransDataSet)
        dataSets.add(defOrgDataSet)

        val data = BarData(dataSets as List<IBarDataSet>?)
        barChart.data = data
        setBarWidth(data, size)
    }

    private fun setBarWidth(data: BarData, size: Int) {
        if (dataSets.size > 1) {
            val groupSpace = 3f
            val barSpace = 0.4f
            Log.d("bar2", dataSets.size.toString())
            barWidth = (-(1 - groupSpace) / dataSets.size - barSpace)
            Log.d("bar", barWidth.toString())
            if (barWidth >= 0) {
                data.barWidth = barWidth
            }

            val groupCount = size
            if (groupCount != -1) {
                barChart.xAxis.axisMinimum = 0f
                barChart.xAxis.axisMaximum = 0 + barChart.barData.getGroupWidth(groupSpace, barSpace) * groupCount
                barChart.xAxis.setCenterAxisLabels(true)
            }

            barChart.groupBars(0f, groupSpace, barSpace)
            barChart.invalidate()
        }
    }


    class MyIndexValueFormatter(values: ArrayList<String>?) : ValueFormatter() {
      private var mValues : ArrayList<String>? = values

        override fun getFormattedValue(value: Float): String {

            return if (value.toInt() > 0 && value.toInt() < mValues?.size!!) {
                Log.d("stats", mValues?.get(value.toInt()).toString())
                mValues?.get(value.toInt()).toString()
            } else {
                Log.d("statss", mValues.toString())
                ""
            }

        }

    }

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
        fragmentNavigator.navigateToDecidesPageFromStatsPage(player)
    }
}



