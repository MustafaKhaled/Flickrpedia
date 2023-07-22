package com.example.flickrpedia.domain.repo

import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.ui.State

interface RegisterUser {
    suspend fun register(email: String, password: String, age: String): State<UserModel>
}