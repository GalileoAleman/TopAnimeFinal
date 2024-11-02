package com.example.topanime.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.topanime.R
import com.example.topanime.databinding.FragmentAnimeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailFragment : Fragment(R.layout.fragment_anime_detail) {
    private val viewModel : AnimeDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAnimeDetailBinding.bind(view)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnFavorite.setOnClickListener { viewModel.onFavClicked() }

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.transparent)

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    binding.updateUi(it)
                }
            }
        }
    }

    private fun FragmentAnimeDetailBinding.updateUi(anime: AnimeDetailViewModel.UiState) {
        toolbar.title = anime.animeDetail?.title
        if(anime.animeDetail?.favorite == true)
            btnFavorite.setImageResource(R.drawable.ic_favorite_on)
        else
            btnFavorite.setImageResource(R.drawable.ic_favorite_off)

        Glide.with(root.context).load(anime.animeDetail?.image)
            .into(animeImageDetail)

        animeSynopsis.text = anime.animeDetail?.synopsis
        animeDetailInfo.setAnimeInfo(anime.animeDetail)
    }
}
