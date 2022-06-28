package com.ws.worldcinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ws.worldcinema.EpisodesAdapter.EpisodeViewHolder
import com.ws.worldcinema.model.Episode
import java.util.*

class EpisodesAdapter : RecyclerView.Adapter<EpisodeViewHolder>() {
    var episodes: ArrayList<Episode>? = null

    fun setupEpisodes(episodes: ArrayList<Episode>?) {
        this.episodes = episodes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        if (episodes!![position].images != null && episodes!![position].images!!.size > 0) Glide.with(holder.itemView.context).load(ImageHelper.getImagePath(
            episodes!![position].images!![0])).into(holder.poster)
        holder.title.text = episodes!![position].name
        holder.description.text = episodes!![position].description
        holder.year.text = episodes!![position].year
    }

    override fun getItemCount(): Int {
        return if (episodes != null) episodes!!.size else 0
    }

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster: ImageView
        var title: TextView
        var description: TextView
        var year: TextView

        init {
            poster = itemView.findViewById(R.id.episodePoster)
            title = itemView.findViewById(R.id.episodeName)
            description = itemView.findViewById(R.id.episodeDescription)
            year = itemView.findViewById(R.id.episodeYear)
        }
    }
}