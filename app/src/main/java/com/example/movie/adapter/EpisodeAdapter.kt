package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.databinding.ItemEpisodeBinding
import com.example.movie.models.Episode

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    private val listEpisodes = ArrayList<Episode>()

    fun setListEpisode(listEpisode: List<Episode>) {
        listEpisodes.clear()
        listEpisodes.addAll(listEpisode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeAdapter.ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeAdapter.ViewHolder, position: Int) {
        val episode = listEpisodes[position]
        holder.binding.titleTv.text = episode.title
        holder.binding.descTv.text = episode.desc
    }

    override fun getItemCount(): Int = listEpisodes.size

    inner class ViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root)
}