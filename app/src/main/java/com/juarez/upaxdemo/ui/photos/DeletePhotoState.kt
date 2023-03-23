package com.juarez.upaxdemo.ui.photos

sealed interface DeletePhotoState {
    data class Loading(val isLoading: Boolean) : DeletePhotoState
    object Success : DeletePhotoState
    object Empty : DeletePhotoState
}