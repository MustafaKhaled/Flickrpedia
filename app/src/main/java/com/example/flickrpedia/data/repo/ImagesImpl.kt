package com.example.flickrpedia.data.repo

import com.example.flickrpedia.data.model.Images
import com.example.flickrpedia.data.remote.ApiService
import com.example.flickrpedia.domain.repo.ImagesRepo
import javax.inject.Inject

class ImagesImpl @Inject constructor(private val apiService: ApiService): ImagesRepo {
    override suspend fun getImages(): Images {
        return apiService.getImages()
    }
}