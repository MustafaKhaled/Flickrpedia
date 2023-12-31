package com.example.flickrpedia.misc

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.example.flickrpedia.R
import com.example.flickrpedia.presentation.UserViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.TimeZone

const val MAX_AGE = 99
const val MIN_AGE = 18
fun getCalenderConstraints(): CalendarConstraints {
    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = today
    calendar[Calendar.YEAR] = calendar[Calendar.YEAR].minus(MAX_AGE)
    val startDate = calendar.timeInMillis

    calendar.timeInMillis = today
    calendar[Calendar.YEAR] = calendar[Calendar.YEAR].minus(MIN_AGE)
    val endDate = calendar.timeInMillis

    return CalendarConstraints.Builder()
        .setOpenAt(endDate)
        .setStart(startDate)
        .setEnd(endDate)
        .build()
}

fun getAge(time: Long): String {
    val calendar: Calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.timeInMillis = time
    return currentYear.minus(calendar.get(Calendar.YEAR)).toString()
}

