package com.example.topanime.ui.animeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.topanime.databinding.AnimeDetailItemBinding
import com.example.topanime.domain.Anime
import com.example.topanime.ui.common.loadUrl
import kotlin.properties.Delegates

class AnimesAdapter(private val animeClickListener: (Anime) -> Unit) : RecyclerView.Adapter<AnimesAdapter.ViewHolder>() {

    var animes: List<Anime> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding:  AnimeDetailItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(anime: Anime){
            binding.animeTitle.text = anime.title
            binding.animeImage.loadUrl(anime.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AnimeDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = animes[position]
        holder.bind(anime)
        holder.itemView.setOnClickListener { animeClickListener(anime) }
    }

    override fun getItemCount() = animes.size

}
