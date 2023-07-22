package com.example.flickrpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.flickrpedia.R
import com.example.flickrpedia.databinding.FragmentImagesListBinding


class ImagesListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImagesListBinding.inflate(inflater, container, false)
        return binding.root
    }
}