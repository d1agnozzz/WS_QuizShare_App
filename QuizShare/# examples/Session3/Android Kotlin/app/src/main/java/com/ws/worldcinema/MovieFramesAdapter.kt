package com.ws.worldcinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ws.worldcinema.MovieFramesAdapter.MovieFrameViewHolder
import java.util.*

class MovieFramesAdapter : RecyclerView.Adapter<MovieFrameViewHolder>() {
    var frames: ArrayList<String>? = null

    fun setMovieFrames(frames: ArrayList<String>?) {
        this.frames = frames
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFrameViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_film_landscape, parent, false)
        return MovieFrameViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieFrameViewHolder, posiion: Int) {
        Glide.with(holder.itemView.context).load(ImageHelper.getImagePath(frames!![position])).into(holder.poster)
    }

    override fun getItemCount(): Int {
        return frames!!.size
    }

    inner class MovieFrameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster: ImageView

        init {
            poster = itemView.findViewById(R.id.poster)
        }
    }
}