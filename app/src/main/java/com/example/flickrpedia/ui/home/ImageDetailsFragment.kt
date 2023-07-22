package com.example.flickrpedia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.flickrpedia.R
import com.example.flickrpedia.databinding.FragmentImageDetailsBinding

class ImageDetailsFragment: Fragment() {
    private lateinit var binding: FragmentImageDetailsBinding
    val args: ImageDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        imageSectionBind()
    }

    private fun imageSectionBind() {
        Glide.with(binding.root).load(args.hit.largeImageUrl).into(binding.image)

        binding.imageSize.label.text = getString(R.string.image_size_label)
        binding.imageSize.value.text = args.hit.imageSize.toString()

        binding.imageType.label.text = getString(R.string.image_type_label)
        binding.imageType.value.text = args.hit.type

        binding.imageTags.label.text = getString(R.string.image_type_label)
        binding.imageTags.value.text = args.hit.tags
    }
}