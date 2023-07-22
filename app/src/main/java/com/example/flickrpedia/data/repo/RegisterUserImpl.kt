package com.example.flickrpedia.data.repo

import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.domain.repo.RegisterUser
import com.example.flickrpedia.misc.State
import javax.inject.Inject

class RegisterUserImpl @Inject constructor() : RegisterUser {
    override suspend fun register(
        email: String,
        password: String,
        age: String
    ): State<UserModel> {
        return if (email != "mustafa@flickrpedia.com") {
            State.Success(UserModel(email, "35"))
        } else {
            State.Error(Throwable(), "The Email already registered, please login")
        }
    }
}