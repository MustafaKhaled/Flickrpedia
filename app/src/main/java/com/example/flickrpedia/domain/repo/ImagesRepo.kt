package com.example.flickrpedia.domain.repo

import com.example.flickrpedia.data.model.Images

interface ImagesRepo {
    suspend fun getImages(): Images
}