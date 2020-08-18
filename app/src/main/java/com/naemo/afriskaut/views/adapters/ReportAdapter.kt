package com.naemo.afriskaut.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.report.ReportData
import com.naemo.afriskaut.utils.AppUtils
import kotlinx.android.synthetic.main.report_result.view.*
import kotlinx.android.synthetic.main.scout_result.view.*
import kotlinx.android.synthetic.main.search_result.view.search_player_image
import kotlinx.android.synthetic.main.search_result.view.search_player_name
import kotlinx.android.synthetic.main.search_result.view.search_result_frame
import javax.inject.Inject

class ReportAdapter(context: Context, private var data: List<ReportData>, private var itemClickListener: ItemClicklistener) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    var context: Context? = null
        @Inject set

    var appUtils: AppUtils? = null
    @Inject set


    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.report_result, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val dataList = data[position]

        val name = dataList.matchname
        val date = dataList.createdAt
        val dateCreated = date.let { appUtils?.convertDate(it) }



        holder.playerName.text = name
        holder.date.text = dateCreated
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(dataList) }
    }


    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       // val playerImage: ImageView = itemView.search_player_image
        val playerName: TextView = itemView.report_name
        val frame: FrameLayout = itemView.report_result_frame
        val date: TextView = itemView.report_date
       // val position: TextView = itemView.position
    }

    interface ItemClicklistener {

        fun onItemClicked(report: ReportData)
    }


}