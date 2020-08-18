package com.naemo.afriskaut.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naemo.afriskaut.R
import com.naemo.afriskaut.db.local.room.following.FollowingData
import kotlinx.android.synthetic.main.radar_result.view.*
import javax.inject.Inject

class RadarAdapter(context: Context, private var data: List<FollowingData>, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<RadarAdapter.RadarViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.radar_result, parent, false)
        return RadarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RadarViewHolder, position: Int) {
        val followingList = data[position]
        val img = followingList.imagePath
        val name = followingList.displayName
        val id = followingList.id

        context?.let { Glide.with(it).load(img).into(holder.playerImage) }
        holder.playerName.text = name
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(followingList) }
        holder.create.setOnClickListener { itemClickListener.goToCreateReportPage(name, id) }
        holder.viewAll.setOnClickListener { itemClickListener.goToAllReportsPage(id) }
    }


    class RadarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerImage: ImageView = itemView.radar_player_image
        val playerName: TextView = itemView.radar_player_name
        val frame: LinearLayout = itemView.radar_result_frame
        val viewAll: ImageButton = itemView.view_report_button
        val create: ImageButton = itemView.create_report_button
    }

    interface ItemClickListener {

        fun onItemClicked(player: FollowingData)

        fun goToCreateReportPage(name: String?, id: String?)

        fun goToAllReportsPage(id: String?)
    }
}