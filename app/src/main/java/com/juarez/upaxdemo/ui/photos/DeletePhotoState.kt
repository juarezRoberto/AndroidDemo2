package com.juarez.upaxdemo.ui.photos

sealed interface DeletePhotoState {
    data class Loading(val isLoading: Boolean) : DeletePhotoState
    data object Success : DeletePhotoState
    data object Empty : DeletePhotoState
}