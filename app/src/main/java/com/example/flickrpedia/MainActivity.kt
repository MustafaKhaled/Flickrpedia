package com.example.flickrpedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flickrpedia.databinding.ActivityMainBinding
import com.example.flickrpedia.presentation.UserViewModel
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
        binding.userPrimaryBtn.setOnClickListener {
            binding.userPrimaryBtn.clearFocus()

        }
    }



    private fun showErrorTextLayout(
        errorState: String,
        textLayoutInput: TextInputLayout
    ) {
        textLayoutInput.error = errorState
    }

    override fun onFocusChange(view: View?, p1: Boolean) {
        when (view?.id) {
            R.id.emailTextInput -> {
                val (isValid, errorMessage) = userViewModel.validateEmail(binding.emailTextInput.text.toString())
                if (view.isFocused.not() && isValid.not()) {
                    showErrorTextLayout(errorMessage.toString(), binding.emailTextLayout)
                } else {
                    showErrorTextLayout("", binding.emailTextLayout)
                }
            }

            R.id.passwordTextInput -> {
                val (isValid, errorMessage) = userViewModel.validatePassword(binding.passwordTextInput.text.toString())
                if (view.isFocused.not() && isValid.not()) {
                    showErrorTextLayout(errorMessage.toString(), binding.passwordTextLayout)
                } else {
                    showErrorTextLayout("", binding.passwordTextLayout)
                }
            }
        }
    }
}