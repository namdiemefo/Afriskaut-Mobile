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
import com.naemo.afriskaut.db.local.room.search.Player
import kotlinx.android.synthetic.main.search_result.view.*
import javax.inject.Inject

class SearchAdapter(context: Context, private var data: List<Player>,  private var itemClickListener: ItemClicklistener) :
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

       // val dbId = dataList.id
        val img = dataList.imagePath
        val name = dataList.displayName
//        val weight = dataList.weight
//        val height = dataList.height
//        val dob = dataList.birthdate
//        val pob = dataList.birthplace
//        val nationality = dataList.nationality
//        val playerPosition = dataList.position?.name
//        val playerId = dataList.playerId
//        val following = dataList.following

        context?.let { Glide.with(it).load(img).into(holder.playerImage) }
        holder.playerName.text = name
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(dataList) }
    }


    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerImage: ImageView = itemView.search_player_image
        val playerName: TextView = itemView.search_player_name
        val frame: LinearLayout = itemView.search_result_frame
    }

    interface ItemClicklistener {

        fun onItemClicked(player: Player)
    }


}