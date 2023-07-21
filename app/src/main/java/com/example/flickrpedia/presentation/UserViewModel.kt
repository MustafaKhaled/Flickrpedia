package com.example.flickrpedia.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    fun validatePassword(password: String): Pair<Boolean, String?> {
        return if (password.trim().isEmpty()) {
            Pair(false, "Password is required")
        } else if (password.trim().length in 12 downTo 6) {
            Pair(false, "password can't be less than 6 or more than 12 character")
        } else {
            Pair(true, "")
        }
    }

    fun validateEmail(email: String): Pair<Boolean, String?> {
        return if (email.trim().isEmpty()) {
            Pair(false, "Email is required")
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not())
            Pair(false, "Invalid Email format")
        else
            Pair(true,"")
    }
}