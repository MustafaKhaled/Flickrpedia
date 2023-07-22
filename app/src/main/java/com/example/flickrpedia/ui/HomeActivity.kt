package com.example.flickrpedia.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flickrpedia.R
import com.example.flickrpedia.databinding.HomeActivityBinding
import com.example.flickrpedia.presentation.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeActivityBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this
    }
}