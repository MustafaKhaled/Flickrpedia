package com.example.flickrpedia.domain.repo

import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.misc.State

interface LoginUser {
    fun login(email: String, password: String): State<UserModel>
}