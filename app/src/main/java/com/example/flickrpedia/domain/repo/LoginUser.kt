package com.example.flickrpedia.domain.repo

interface LoginUser {
    fun login(email: String, password: String)
}