package com.naemo.afriskaut.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naemo.afriskaut.R
import com.naemo.afriskaut.db.local.room.scout.Player
import kotlinx.android.synthetic.main.scout_result.view.*
import javax.inject.Inject

class ScoutAdapter(context: Context, private var data: List<Player>, private var itemClickListener: ItemClicklistener) :
    RecyclerView.Adapter<ScoutAdapter.ScoutViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scout_result, parent, false)
        return ScoutViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ScoutViewHolder, position: Int) {
        val dataList = data[position]

        val img = dataList.imagePath
        val name = dataList.displayName
        val playerPosition = dataList.position?.name
        val age = dataList.age

        context?.let { Glide.with(it).load(img).into(holder.playerImage) }
        holder.playerName.text = name
        holder.playerAge.text = age.toString()
        holder.playerPosition.text = playerPosition
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(dataList) }
    }


    class ScoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerImage: ImageView = itemView.scout_player_image
        val playerName: TextView = itemView.scout_player_name
        val frame: FrameLayout = itemView.scout_result_frame
        val playerPosition: TextView = itemView.scout_player_position
        val playerAge: TextView = itemView.scout_player_age
    }

    interface ItemClicklistener {

        fun onItemClicked(player: Player)
    }


}