package com.example.flickrpedia.data.repo

import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.domain.repo.LoginUser
import com.example.flickrpedia.ui.State
import javax.inject.Inject

class LoginUserImpl @Inject constructor(): LoginUser {
    override fun login(email: String, password: String): State<UserModel> {
        return if (email != "mustafa@flickrpedia.com") {
            State.Success(UserModel(email, null))
        } else {
            State.Error(Throwable(), "username and password are not matching")
        }
    }
}