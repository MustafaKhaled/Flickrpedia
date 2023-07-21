package com.example.flickrpedia.presentation

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrpedia.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val application: Application) :
    AndroidViewModel(application) {
    private val _userType = MutableStateFlow(UserType.LOGIN)
    val userType = _userType.asStateFlow()

    fun changeUserType(userType: UserType) {
        _userType.value = userType
    }

    fun validatePassword(password: String): Pair<Boolean, String?> {
        return if (password.trim().isEmpty()) {
            Pair(false, application.applicationContext.getString(R.string.password_is_required))
        } else if (password.trim().length < 6 || password.trim().length > 12) {
            Pair(
                false,
                application.applicationContext.getString(R.string.password_can_t_be_less_than_6_or_more_than_12_character)
            )
        } else {
            Pair(true, "")
        }
    }

    fun validateEmail(email: String): Pair<Boolean, String?> {
        return if (email.trim().isEmpty())
            Pair(false, application.applicationContext.getString(R.string.email_is_required))
        else if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not())
            Pair(false, application.applicationContext.getString(R.string.invalid_email_format))
        else
            Pair(true, "")
    }

    fun validateAge(age: String): Pair<Boolean, String> {
        return if (age.trim().isEmpty()) {
            Pair(false, "Age is required")
        } else
            Pair(true, "")
    }

    enum class UserType {
        LOGIN, REGISTER
    }
}