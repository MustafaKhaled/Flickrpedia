package com.example.flickrpedia

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.example.flickrpedia.databinding.ActivityMainBinding
import com.example.flickrpedia.presentation.UserViewModel
import com.example.flickrpedia.ui.State
import com.example.flickrpedia.ui.getAge
import com.example.flickrpedia.ui.getCalenderConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnFocusChangeListener {

    lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        setupListeners()
        lifecycle.coroutineScope.launch {
            userViewModel.uiState.collect {
                when (it) {
                    is UserViewModel.UserUiState.ErrorRegister -> Toast.makeText(
                        applicationContext,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                    is UserViewModel.UserUiState.ErrorLogin -> Toast.makeText(
                        applicationContext,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                    is UserViewModel.UserUiState.Loading -> binding.progressBar.visibility =
                        View.VISIBLE

                    is UserViewModel.UserUiState.SuccessLogin -> {}
                    is UserViewModel.UserUiState.SuccessRegistration -> {}
                    else -> {}
                }
            }
        }

    }

    private fun setupListeners() {
        binding.emailTextInput.onFocusChangeListener = this
        binding.passwordTextInput.onFocusChangeListener = this
        binding.userPrimaryBtn.onFocusChangeListener = this
        binding.ageTextInput.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().setCalendarConstraints(
                getCalenderConstraints()
            ).build()
            (this as? AppCompatActivity)?.let {
                picker.show(it.supportFragmentManager, picker.toString())
                picker.addOnPositiveButtonClickListener { dateTimeNow ->
                    binding.ageTextInput.setText(getAge(dateTimeNow))
                    resetError(binding.ageTextLayout)
                }
            }
        }
        binding.userPrimaryBtn.setOnClickListener {
            requestFocus(it)
            when (userViewModel.userType.value) {
                UserViewModel.UserType.LOGIN -> {
                    if (validateForm(binding.emailTextInput) &&
                        validateForm(binding.passwordTextInput)
                    ) {
                        resetErrors()
                        userViewModel.login(
                            email = binding.emailTextInput.text.toString(),
                            password = binding.passwordTextInput.text.toString()
                        )
                    }
                }

                UserViewModel.UserType.REGISTER -> {
                    if (validateForm(binding.emailTextInput) &&
                        validateForm(binding.passwordTextInput) &&
                        validateForm(binding.ageTextInput)
                    ) {
                        resetErrors()
                        userViewModel.register(
                            email = binding.emailTextInput.text.toString(),
                            password = binding.passwordTextInput.text.toString(),
                            age = binding.ageTextInput.toString()
                        )
                    }
                }
            }

        }
        binding.switchUserType.setOnClickListener {
            if (userViewModel.userType.value == UserViewModel.UserType.REGISTER)
                userViewModel.changeUserType(UserViewModel.UserType.LOGIN)
            else
                userViewModel.changeUserType(UserViewModel.UserType.REGISTER)

            resetErrors()
        }
    }

    private fun resetErrors() {
        resetError(binding.ageTextLayout)
        resetError(binding.passwordTextLayout)
        resetError(binding.emailTextLayout)
    }

    private fun showErrorTextLayout(
        errorState: String = "",
        textLayoutInput: TextInputLayout
    ) {
        textLayoutInput.error = errorState
    }

    private fun resetError(textLayoutInput: TextInputLayout) {
        textLayoutInput.error = ""
    }

    override fun onFocusChange(view: View?, p1: Boolean) {
        validateForm(view)
    }

    private fun validateForm(view: View?): Boolean {
        when (view?.id) {
            R.id.emailTextInput -> {
                val (isValid, errorMessage) = userViewModel.validateEmail(binding.emailTextInput.text.toString())
                return validateTextInput(view, binding.emailTextLayout, isValid, errorMessage)
            }

            R.id.passwordTextInput -> {
                val (isValid, errorMessage) = userViewModel.validatePassword(binding.passwordTextInput.text.toString())
                return validateTextInput(view, binding.passwordTextLayout, isValid, errorMessage)
            }

            R.id.ageTextInput -> {
                val (isValid, errorMessage) = userViewModel.validateAge(binding.ageTextInput.text.toString())
                return validateTextInput(view, binding.ageTextLayout, isValid, errorMessage)
            }
        }
        return false
    }

    private fun validateTextInput(
        view: View,
        textLayoutLayout: TextInputLayout,
        isValid: Boolean,
        errorMessage: String?
    ): Boolean {
        return if (view.isFocused.not() && isValid.not()) {
            if (errorMessage != null) {
                showErrorTextLayout(errorMessage, textLayoutLayout)
            }
            false
        } else {
            resetError(textLayoutLayout)
            true
        }
    }

    private fun requestFocus(it: View) {
        it.isFocusableInTouchMode = true
        it.requestFocus()
        it.isFocusableInTouchMode = false
    }
}