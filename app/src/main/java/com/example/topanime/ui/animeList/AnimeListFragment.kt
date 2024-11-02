package com.example.topanime.ui.animeList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.topanime.R
import com.example.topanime.databinding.FragmentAnimeListBinding
import com.example.topanime.ui.common.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : Fragment(R.layout.fragment_anime_list) {

    private val viewModel : AnimeListViewModel by viewModels()
    private val animeCategoriesAdapter = AnimeCategoriesAdapter{ animeListState.onAnimeClicked(it) }

    private lateinit var animeListState: AnimeListState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAnimeListBinding.bind(view)
        binding.recyclerCategories.adapter = animeCategoriesAdapter

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.topAppBar)

        animeListState = AnimeListState(findNavController())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    binding.updateUi(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.primary)
    }

    private fun FragmentAnimeListBinding.updateUi(state: AnimeListViewModel.UiState) {
        progress.visible = state.loading
        state.animeCategories?.let { animeCategoriesAdapter.submitList(it) }
        error.text = if(state.error) getString(R.string.error_anime_list) else ""
    }
}
