package com.juarez.upaxdemo.ui.photo

sealed class DeletePhotoState {
    data class Loading(val isLoading: Boolean) : DeletePhotoState()
    object Success : DeletePhotoState()
    object Empty : DeletePhotoState()
}