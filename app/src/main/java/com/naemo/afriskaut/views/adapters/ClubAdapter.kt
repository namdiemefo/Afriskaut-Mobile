package com.naemo.afriskaut.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naemo.afriskaut.R
import com.naemo.afriskaut.api.models.search.Stats
import com.naemo.afriskaut.utils.AppUtils
import kotlinx.android.synthetic.main.team_result.view.*
import javax.inject.Inject

class ClubAdapter(context: Context, private var club: ArrayList<String>, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {

    var appUtils = AppUtils()
        @Inject set

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_result, parent, false)
        return ClubViewHolder(view)
    }

    override fun getItemCount(): Int {
        return club?.size ?: 0
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
//        val iDs = ArrayList<Int>()
//        val teams = club?.values?.toList()
//        val logos = logo?.values?.toList()
//        val teamName = teams?.get(position)
//        val teamLogo = logos?.get(position)
//        holder.teamName.text = teamName
//        context?.let { Glide.with(it).load(teamLogo).into(holder.teamImage) }
//        val teamId = club?.keys
//        teamId?.let {
//            for (id in it) {
//                iDs.add(id)
//            }
//        }
//        val id = iDs[position]
//        holder.teamFrame.setOnClickListener{ itemClickListener.onItemClicked(id)}

        val clubs = club[position]
        holder.teamName.text = clubs
        holder.teamFrame.setOnClickListener { itemClickListener.onClubItemClicked(clubs) }


    }

    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val teamName: TextView = itemView.team_name
        val teamImage: ImageView = itemView.team_image
        val teamFrame: LinearLayout = itemView.team_result_frame
    }

    interface ItemClickListener {

        fun onClubItemClicked(name: String)
    }
}