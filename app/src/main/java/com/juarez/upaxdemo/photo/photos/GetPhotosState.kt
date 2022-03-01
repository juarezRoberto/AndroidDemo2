package com.juarez.upaxdemo.photo.photos

import com.juarez.upaxdemo.photo.data.Photo

sealed class GetPhotosState {
    data class Loading(val isLoading: Boolean) : GetPhotosState()
    data class Success(val data: List<Photo>) : GetPhotosState()
    data class Error(val message: String) : GetPhotosState()
    object Empty : GetPhotosState()
}