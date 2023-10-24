package com.juarez.upaxdemo.ui.photo

sealed interface SavePhotoState {
    data class Loading(val isLoading: Boolean) : SavePhotoState
    data object Success : SavePhotoState
}