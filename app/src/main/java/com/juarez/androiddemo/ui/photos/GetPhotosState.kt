package com.juarez.androiddemo.ui.photos

import com.juarez.androiddemo.domain.models.Photo

sealed interface GetPhotosState {
    data class Loading(val isLoading: Boolean) : GetPhotosState
    data class Success(val data: List<Photo>) : GetPhotosState
    data class Error(val message: String) : GetPhotosState
    data object Empty : GetPhotosState
}