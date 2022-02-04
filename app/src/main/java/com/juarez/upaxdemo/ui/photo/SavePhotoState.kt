package com.juarez.upaxdemo.ui.photo

sealed class SavePhotoState {
    data class Loading(val isLoading: Boolean) : SavePhotoState()
    object Success : SavePhotoState()
}