package com.juarez.upaxdemo.photo.photo

sealed class SavePhotoState {
    data class Loading(val isLoading: Boolean) : SavePhotoState()
    object Success : SavePhotoState()
}