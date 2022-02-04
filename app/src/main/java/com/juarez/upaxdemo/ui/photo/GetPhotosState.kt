package com.juarez.upaxdemo.ui.photo

import com.juarez.upaxdemo.data.models.Photo

sealed class GetPhotosState {
    data class Loading(val isLoading: Boolean) : GetPhotosState()
    data class Success(val data: List<Photo>) : GetPhotosState()
    data class Error(val message: String) : GetPhotosState()
    object Empty : GetPhotosState()
}