package com.example.flickrpedia.data.repo

import android.content.Context
import com.example.flickrpedia.R
import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.domain.repo.RegisterUser
import com.example.flickrpedia.misc.State
import javax.inject.Inject

class RegisterUserImpl @Inject constructor(private val context: Context) : RegisterUser {
    override suspend fun register(
        email: String,
        password: String,
        age: String
    ): State<UserModel> {
        return if (email != "mustafa@flickrpedia.com") {
            State.Success(UserModel(email, "35"))
        } else {
            State.Error(Throwable(), context.getString(R.string.the_email_already_registered_please_login_error_msg))
        }
    }
}