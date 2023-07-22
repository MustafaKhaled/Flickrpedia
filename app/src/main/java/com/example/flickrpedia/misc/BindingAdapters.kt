package com.example.flickrpedia.misc

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.flickrpedia.R
import com.example.flickrpedia.presentation.UserViewModel
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("android:setBottomText")
fun TextView.setBottomText(userType: UserViewModel.UserType) {
    if(userType == UserViewModel.UserType.LOGIN)
        this.text = this.context.getString(R.string.dont_have_an_account)
    else
        this.text = this.context.getString(R.string.already_has_an_account)
}

@BindingAdapter("android:setVisibility")
fun TextInputLayout.setVisibility(userType: UserViewModel.UserType) {
    visibility = if (userType == UserViewModel.UserType.LOGIN) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("android:setText")
fun AppCompatButton.setText(userType: UserViewModel.UserType) {
    if(userType == UserViewModel.UserType.LOGIN)
        this.text = this.context.getString(R.string.login_btn_text)
    else
        this.text = this.context.getString(R.string.register_btn_text)
}

@BindingAdapter("android:imageUrl")
fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).into(this)
}