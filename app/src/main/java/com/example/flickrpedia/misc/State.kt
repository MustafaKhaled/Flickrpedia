package com.example.flickrpedia.misc

sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error<out T>(val throwable: Throwable, val msg: String? = null, val data: T? = null) :
        State<T>()
    object Loading : State<Nothing>()
}