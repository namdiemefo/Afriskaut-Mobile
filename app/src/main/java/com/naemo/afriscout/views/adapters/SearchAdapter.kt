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
import com.naemo.afriscout.db.local.room.search.Data
import kotlinx.android.synthetic.main.search_result.view.*
import javax.inject.Inject

class SearchAdapter(context: Context, private var data: List<Data>,  private var itemClickListener: ItemClicklistener) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val dataList = data[position]

        val dbId = dataList.vId
        val img = dataList.image
        val name = dataList.name
        val height = dataList.height
        val dob = dataList.dob
        val team = dataList.team
        val nationality = dataList.nationality
        val playerPosition = dataList.position
        val id = dataList.id
        val playerId = dataList.playerId
        val following = dataList.following

        context?.let { Glide.with(it).load(img).into(holder.playerImage) }
        holder.playerName.text = name
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(dbId, id, img, playerId, name, height, dob, team, nationality, playerPosition, following) }
    }


    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerImage: ImageView = itemView.search_player_image
        val playerName: TextView = itemView.search_player_name
        val frame: LinearLayout = itemView.search_result_frame
    }

    interface ItemClicklistener {

        fun onItemClicked(dBid: Int, id: String, img: String, playerId: Int, name: String, height: String, dob: String, team: String, nationality: String, position: String, Follow: Boolean)
    }


}