package com.example.flickrpedia.data.remote

import arrow.core.Either
import com.example.flickrpedia.data.model.Images
import com.example.flickrpedia.misc.State
import retrofit2.http.GET
import java.lang.Exception

interface ApiService {
    @GET("api?key=38406535-fdc605eb137b55abe424cdf13")
    suspend fun getImages(): Images
}