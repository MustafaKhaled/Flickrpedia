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
            if (validateTextInput(binding.emailTextInput) &&
                validateTextInput(binding.passwordTextInput) &&
                validateTextInput(binding.ageTextInput)
            ) {
                Log.d("MainActivity", "setupListeners: Success")
            } else {
                Log.d("MainActivity", "setupListeners: Failure")
            }
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
        validateTextInput(view)
    }

    private fun validateTextInput(view: View?): Boolean {
        when (view?.id) {
            R.id.emailTextInput -> {
                val (isValid, errorMessage) = userViewModel.validateEmail(binding.emailTextInput.text.toString())
                if (view.isFocused.not() && isValid.not()) {
                    showErrorTextLayout(errorMessage.toString(), binding.emailTextLayout)
                    return false
                } else {
                    resetError(binding.emailTextLayout)
                    return true
                }
            }

            R.id.passwordTextInput -> {
                val (isValid, errorMessage) = userViewModel.validatePassword(binding.passwordTextInput.text.toString())
                if (view.isFocused.not() && isValid.not()) {
                    showErrorTextLayout(errorMessage.toString(), binding.passwordTextLayout)
                    return false
                } else {
                    resetError(binding.passwordTextLayout)
                    return true
                }
            }

            R.id.ageTextInput -> {
                val (isValid, errorMessage) = userViewModel.validateAge(binding.ageTextInput.text.toString())
                if (view.isFocused.not() && isValid.not()) {
                    showErrorTextLayout(errorMessage, binding.ageTextLayout)
                    return false
                } else {
                    resetError(binding.ageTextLayout)
                    return true
                }
            }
        }
        return false
    }

    private fun requestFocus(it: View) {
        it.isFocusableInTouchMode = true
        it.requestFocus()
        it.isFocusableInTouchMode = false
    }
}