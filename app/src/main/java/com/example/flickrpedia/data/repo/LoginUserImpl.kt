package com.example.flickrpedia.data.repo

import android.content.Context
import com.example.flickrpedia.R
import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.domain.repo.LoginUser
import com.example.flickrpedia.misc.State
import javax.inject.Inject

class LoginUserImpl @Inject constructor(private val context: Context): LoginUser {
    override fun login(email: String, password: String): State<UserModel> {
        // just for mocking
        // To be able to login, use this email and any password.
        return if (email == "mustafa@flickrpedia.com") {
            State.Success(UserModel(email, null))
        } else {
            State.Error(Throwable(), context.getString(R.string.username_and_password_are_not_matching))
        }
    }
}