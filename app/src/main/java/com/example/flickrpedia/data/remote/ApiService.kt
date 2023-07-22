package com.example.flickrpedia.data.remote

import com.example.flickrpedia.data.model.Images
import retrofit2.http.GET

interface ApiService {
    @GET("api?key=38406535-fdc605eb137b55abe424cdf13")
    suspend fun getImages(): Images
}