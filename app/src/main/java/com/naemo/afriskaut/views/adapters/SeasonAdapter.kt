package com.naemo.afriskaut.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naemo.afriskaut.R
import kotlinx.android.synthetic.main.team_result.view.*
import javax.inject.Inject

class SeasonAdapter(context: Context, private var season: Map<Int, String>?, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    var seasonName = season?.values?.toList()
    var seasonId = season?.keys?.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_result, parent, false)
        return SeasonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return season?.size ?: 0
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val sznName = seasonName?.get(position)
        val sznId = seasonId?.get(position)
        holder.seasonName.text = sznName
        holder.seasonFrame.setOnClickListener { itemClickListener.onItemClicked(sznId) }
    }


    class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val seasonName: TextView = itemView.team_name
      //  val seasonImage: ImageView = itemView.team_image
        val seasonFrame: LinearLayout = itemView.team_result_frame
    }

    interface ItemClickListener {

        fun onItemClicked(id: Int?)
    }
}