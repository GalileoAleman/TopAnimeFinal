package com.example.topanime.ui.animeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topanime.databinding.AnimeCategoriesItemBinding
import com.example.topanime.domain.Anime
import com.example.topanime.domain.AnimeCategories

class AnimeCategoriesAdapter(private val animeCatClickListener: ((Anime) -> Unit)) :
    ListAdapter<AnimeCategories, AnimeCategoriesAdapter.ViewHolder>(DiffUtilCallback) {

    class ViewHolder(private val binding: AnimeCategoriesItemBinding) : RecyclerView.ViewHolder(binding.root){
        private lateinit var animeCatClickListener: ((Anime) -> Unit)

        fun bind(animeCategories: AnimeCategories, animeCatClickListener: ((Anime) -> Unit)) {
            binding.categorieTitle.text = animeCategories.title

            val animesAdapter = AnimesAdapter(animeCatClickListener)

            binding.recyclerAnimes.adapter = animesAdapter

            setAnimeCatClickListener(animeCatClickListener)

            animesAdapter.animes = animeCategories.categories
        }

        fun setAnimeCatClickListener(listener: ((Anime) -> Unit)) {
            animeCatClickListener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AnimeCategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animeCategories = getItem(position)
        holder.bind(animeCategories, animeCatClickListener)
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<AnimeCategories>(){
    override fun areItemsTheSame(oldItem: AnimeCategories, newItem: AnimeCategories): Boolean = oldItem.title == newItem.title
    override fun areContentsTheSame(oldItem: AnimeCategories, newItem: AnimeCategories): Boolean = oldItem == newItem
}
