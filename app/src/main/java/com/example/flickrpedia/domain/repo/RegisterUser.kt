package com.example.flickrpedia.domain.repo

interface RegisterUser {
    fun register(email: String, password: String, age: String)
}