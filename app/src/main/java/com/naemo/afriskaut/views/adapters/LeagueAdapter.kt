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
import com.naemo.afriskaut.db.local.room.team.Team
import com.naemo.afriskaut.utils.AppUtils
import kotlinx.android.synthetic.main.team_result.view.*
import javax.inject.Inject

class LeagueAdapter(context: Context, private var league: Team, private var itemClickListener: ItemClickListen): RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    var appUtils = AppUtils()
        @Inject set

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }


    private val leagueId = league.teamName
    private val leagueLogo = league.flag

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_result, parent, false)
        return LeagueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leagueId?.size ?: 0
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val leagueName = leagueLogo?.keys?.toList()
        val logos = leagueLogo?.values?.toList()
        val name = leagueName?.get(position)
        val teamLogo = logos?.get(position)

        holder.teamName.text = name
        context?.let { Glide.with(it).load(teamLogo).into(holder.teamImage) }
        val ids = leagueId?.keys?.toList()
        val id = ids?.get(position)
        holder.teamFrame.setOnClickListener{ itemClickListener.onItemClick(id)}
    }

    class LeagueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val teamName: TextView = itemView.team_name
        val teamImage: ImageView = itemView.team_image
        val teamFrame: LinearLayout = itemView.team_result_frame
    }

    interface ItemClickListen {

        fun onItemClick(id: Int?)
    }

}