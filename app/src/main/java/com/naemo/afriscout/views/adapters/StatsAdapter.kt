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

class StatsAdapter(context: Context, private var playerStats: List<PlayerStats>): RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

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
        return playerStats.size
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        var keys = mutableListOf<PlayerStats>()
        for (k in playerStats) {
            keys.add(k)
        }
       // val sum = playerStats.get(1).
        val stats = playerStats[position]
        val appearances = stats.appearences
        holder.statScore.text = appearances.toString()
    }


    class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val stat: TextView = itemView.stat
        val statScore: TextView = itemView.stat_score
    }


}