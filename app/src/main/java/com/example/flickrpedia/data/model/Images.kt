package com.example.flickrpedia.data.model

import com.google.gson.annotations.SerializedName

data class Images(
    val total: Long,
    val totalHits: Long,
    val hits: List<Hit>,
)

data class Hit(
    val id: Long,
    @SerializedName("pageURL")
    val pageUrl: String,
    val type: String,
    val tags: String,
    @SerializedName("previewURL")
    val previewUrl: String,
    val previewWidth: Long,
    val previewHeight: Long,
    @SerializedName("webformatURL")
    val webformatUrl: String,
    val webformatWidth: Long,
    val webformatHeight: Long,
    @SerializedName("largeImageURL")
    val largeImageUrl: String,
    val imageWidth: Long,
    val imageHeight: Long,
    val imageSize: Long,
    val views: Long,
    val downloads: Long,
    val collections: Long,
    val likes: Long,
    val comments: Long,
    @SerializedName("user_id")
    val userId: Long,
    val user: String,
    @SerializedName("userImageURL")
    val userImageUrl: String,
)
