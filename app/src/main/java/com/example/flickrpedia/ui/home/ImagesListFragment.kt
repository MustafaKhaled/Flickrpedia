package com.example.flickrpedia.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.flickrpedia.databinding.FragmentImagesListBinding
import com.example.flickrpedia.presentation.HomeViewModel
import com.example.flickrpedia.presentation.HomeViewModel.ImagesUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesListFragment : Fragment() {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImagesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.loadImages()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.uiState.collect { state ->
                when (state) {
                    is ImagesUiState.Error -> {}
                    is ImagesUiState.Loading -> { }
                    is ImagesUiState.Success -> {
                        Log.d("ImagesListFragment", "onViewCreated: ${state.images.hits.size}")
                    }
                }
            }

        }
    }
}