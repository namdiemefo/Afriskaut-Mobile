package com.naemo.afriscout.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naemo.afriscout.R
import com.naemo.afriscout.db.local.room.following.FollowingData
import kotlinx.android.synthetic.main.search_result.view.*
import javax.inject.Inject

class RadarAdapter(context: Context, private var data: List<FollowingData>, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<RadarAdapter.RadarViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result, parent, false)
        return RadarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RadarViewHolder, position: Int) {
        val followingList = data[position]
        val dbId = followingList.id
        val img = followingList.image
        val name = followingList.fullname
        val height = followingList.height
        val dob = followingList.dob
        val team = followingList.team
        val nationality = followingList.nationality
        val playerPosition = followingList.position
        val id = followingList._id
        val playerId = followingList.playerId?.toInt()
        val following = followingList.following

        context?.let { Glide.with(it).load(img).into(holder.playerImage) }
        holder.playerName.text = name
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(dbId, id, img, playerId, name, height, dob, team, nationality, playerPosition, following) }
    }


    class RadarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerImage: ImageView = itemView.search_player_image
        val playerName: TextView = itemView.search_player_name
        val frame: LinearLayout = itemView.search_result_frame
    }

    interface ItemClickListener {

        fun onItemClicked(dBid: Int?, id: String?, img: String?, playerId: Int?, name: String?, height: String?, dob: String?, team: String?, nationality: String?, position: String?, Follow: Boolean?)
    }
}