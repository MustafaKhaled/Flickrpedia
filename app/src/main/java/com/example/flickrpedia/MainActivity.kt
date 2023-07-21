package com.example.flickrpedia

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flickrpedia.databinding.ActivityMainBinding
import com.example.flickrpedia.presentation.UserViewModel
import com.example.flickrpedia.ui.getAge
import com.example.flickrpedia.ui.getCalenderConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(), View.OnFocusChangeListener {

    lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        setupListeners()
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
            if (validateForm(binding.emailTextInput) &&
                validateForm(binding.passwordTextInput) &&
                validateForm(binding.ageTextInput)
            ) {
                Log.d("MainActivity", "setupListeners: Success")
            } else {
                Log.d("MainActivity", "setupListeners: Failure")
            }
        }
        binding.switchUserType.setOnClickListener {
            if (userViewModel.userType.value == UserViewModel.UserType.REGISTER)
                userViewModel.changeUserType(UserViewModel.UserType.LOGIN)
            else
                userViewModel.changeUserType(UserViewModel.UserType.REGISTER)

            resetError(binding.ageTextLayout)
            resetError(binding.passwordTextLayout)
            resetError(binding.emailTextLayout)
        }
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
                return  validateTextInput(view, binding.emailTextLayout, isValid, errorMessage)
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
            resetError(binding.ageTextLayout)
            true
        }
    }

    private fun requestFocus(it: View) {
        it.isFocusableInTouchMode = true
        it.requestFocus()
        it.isFocusableInTouchMode = false
    }
}