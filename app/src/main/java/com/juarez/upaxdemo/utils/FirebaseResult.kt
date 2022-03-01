package com.juarez.upaxdemo.utils


sealed class FirebaseResult<out T> {
    data class Loading(val isLoading: Boolean) : FirebaseResult<Nothing>()
    data class Success<out T>(val data: T) : FirebaseResult<T>()
}