package com.example.flickrpedia.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrpedia.data.model.Hit
import com.example.flickrpedia.databinding.FragmentImagesListBinding
import com.example.flickrpedia.presentation.HomeViewModel
import com.example.flickrpedia.presentation.HomeViewModel.ImagesUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesListFragment : Fragment(), ImagesAdapter.OnItemClickListener {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var binding: FragmentImagesListBinding
    private lateinit var adapter: ImagesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagesListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.loadImages()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.uiState.collect { state ->
                when (state) {
                    is ImagesUiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    is ImagesUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ImagesUiState.Success -> {
                        bindImagesList(state.images.hits)
                    }
                }
            }

        }
    }

    private fun bindImagesList(images: List<Hit>) {
        adapter = ImagesAdapter(images, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(hit: Hit) {

    }
}