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
import com.naemo.afriskaut.db.local.room.suggest.SuggestData
import kotlinx.android.synthetic.main.scout_result.view.*
import javax.inject.Inject

class SuggestionAdapter(context: Context, private var data: List<SuggestData>, private var itemClickListener: ItemClicklistener) : RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>() {

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scout_result, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val dataList = data[position]


        val img = dataList.imagePath
        val name = dataList.displayName
        val nationality = dataList.nationality
        val playerPosition = dataList.position?.name

        context?.let { Glide.with(it).load(img).into(holder.playerImage) }
        holder.playerName.text = name
        holder.nationality.text = nationality
        holder.age.text = playerPosition
        holder.frame.setOnClickListener { itemClickListener.onItemClicked(dataList) }
    }


    class SuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerImage: ImageView = itemView.scout_player_image
        val playerName: TextView = itemView.scout_player_name
        val frame: FrameLayout = itemView.scout_result_frame
        val nationality: TextView = itemView.scout_player_position
        val age: TextView = itemView.scout_player_age
    }

    interface ItemClicklistener {

        fun onItemClicked(player: SuggestData)
    }



}