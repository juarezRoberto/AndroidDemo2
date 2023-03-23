package com.juarez.upaxdemo.ui.photos

import com.juarez.upaxdemo.domain.models.Photo

sealed interface GetPhotosState {
    data class Loading(val isLoading: Boolean) : GetPhotosState
    data class Success(val data: List<Photo>) : GetPhotosState
    data class Error(val message: String) : GetPhotosState
    object Empty : GetPhotosState
}