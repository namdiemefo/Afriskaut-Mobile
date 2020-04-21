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

class NationAdapter(context: Context, private var team: Team, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<NationAdapter.NationViewHolder>() {

    var appUtils = AppUtils()
        @Inject set

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val country = appUtils.checkIfCountry(team.teamName)
    @RequiresApi(Build.VERSION_CODES.N)
    val logo = appUtils.checkIfsCountry(team.flag)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_result, parent, false)
        return NationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return country?.size ?: 1
    }

    override fun onBindViewHolder(holder: NationViewHolder, position: Int) {
        Log.d("count", country?.size.toString())
        country?.let {
            if (it.isEmpty()) {
                holder.teamName.text = context?.getString(R.string.not_found)
            }
        }

        val iDs = ArrayList<Int>()
        val teams = country?.values?.toList()
        val logos = logo?.values?.toList()
        val teamName = teams?.get(position)
        val teamLogo = logos?.get(position)
        Log.d("country", teamName.toString())
        holder.teamName.text = teamName
        context?.let { Glide.with(it).load(teamLogo).into(holder.teamImage) }
        val teamId = country?.keys
        teamId?.let {
            for (id in it) {
                iDs.add(id)
            }
        }
        val id = iDs[position]
        Log.d("country2", iDs.toString())
        holder.teamFrame.setOnClickListener{ itemClickListener.onItemClicked(id)}
    }

    class NationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val teamName: TextView = itemView.team_name
        val teamImage: ImageView = itemView.team_image
        val teamFrame: LinearLayout = itemView.team_result_frame
    }

    interface ItemClickListener {

        fun onItemClicked(id: Int)
    }
}