package com.naemo.afriscout.views.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naemo.afriscout.R
import com.naemo.afriscout.db.local.room.team.Team
import com.naemo.afriscout.utils.AppUtils
import kotlinx.android.synthetic.main.team_result.view.*
import java.util.ArrayList
import javax.inject.Inject

class TeamAdapter(context: Context, private var team: Team, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>()  {

    var appUtils = AppUtils()
        @Inject set

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val club = appUtils.checkIfClub(team.teamName)
    @RequiresApi(Build.VERSION_CODES.N)
    val logo = appUtils.checkIfsClub(team.flag)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_result, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return club?.size ?: 0
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val iDs = ArrayList<Int>()
        val teams = club?.values?.toList()
        val logos = logo?.values?.toList()
        val teamName = teams?.get(position)
        val teamLogo = logos?.get(position)
        Log.d("clubs", teamName.toString())
        holder.teamName.text = teamName
        context?.let { Glide.with(it).load(teamLogo).into(holder.teamImage) }
        val teamId = club?.keys
        teamId?.let {
            for (id in it) {
                iDs.add(id)
            }
        }
        val id = iDs[position]
        Log.d("clubs2", iDs.toString())
        holder.teamFrame.setOnClickListener{ itemClickListener.onItemClicked(id)}

    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val teamName: TextView = itemView.team_name
        val teamImage: ImageView = itemView.team_image
        val teamFrame: LinearLayout = itemView.team_result_frame
    }

    interface ItemClickListener {

        fun onItemClicked(id: Int)
    }
}