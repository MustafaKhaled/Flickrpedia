package com.example.flickrpedia.presentation

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrpedia.R
import com.example.flickrpedia.data.model.UserModel
import com.example.flickrpedia.domain.repo.LoginUser
import com.example.flickrpedia.domain.repo.RegisterUser
import com.example.flickrpedia.misc.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val application: Application, private val registerUser: RegisterUser, private val loginUser: LoginUser) :
    AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<UserUiState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _userType = MutableStateFlow(UserType.LOGIN)
    val userType = _userType.asStateFlow()

    fun register(email: String, password: String, age:String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            delay(3000) // Fake Loading
            val result = registerUser.register(email,password,age)
            _uiState.value = when(result) {
                is State.Error -> UserUiState.ErrorRegister(result.msg.toString())
                is State.Loading ->  { UserUiState.Loading }
                is State.Success -> UserUiState.SuccessRegistration(result.data)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            delay(3000) // Fake Loading
            val result = loginUser.login(email,password)
            _uiState.value = when(result) {
                is State.Error -> UserUiState.ErrorLogin(result.msg.toString())
                is State.Loading ->  { UserUiState.Loading }
                is State.Success -> UserUiState.SuccessLogin(result.data.email)
            }
        }
    }

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
            Pair(false, application.applicationContext.getString(R.string.age_is_required))
        } else
            Pair(true, "")
    }

    sealed class UserUiState {
        object Loading : UserUiState()
        data class SuccessRegistration(val userModel: UserModel): UserUiState()
        data class SuccessLogin(val email: String): UserUiState()
        data class ErrorRegister(val message: String): UserUiState()
        data class ErrorLogin(val message: String): UserUiState()
    }
    enum class UserType {
        LOGIN, REGISTER
    }
}