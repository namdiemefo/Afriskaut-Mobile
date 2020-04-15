package com.naemo.afriscout.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naemo.afriscout.R
import com.naemo.afriscout.db.local.room.stats.PlayerStats
import kotlinx.android.synthetic.main.stats_result.view.*
import javax.inject.Inject

class StatsAdapter(context: Context, private var playerStats: PlayerStats): RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stats_result, parent, false)
        return StatsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val stats = arrayListOf(playerStats)
        val appearances = stats[position].appearences
        holder.statScore.text = appearances.toString()
    }


    class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val stat: TextView = itemView.stat
        val statScore: TextView = itemView.stat_score
    }


}