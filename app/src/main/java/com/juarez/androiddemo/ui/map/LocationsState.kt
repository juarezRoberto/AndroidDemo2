package com.juarez.androiddemo.ui.map

import com.juarez.androiddemo.domain.models.Location

sealed interface LocationsState {
    data class Loading(val isLoading: Boolean) : LocationsState
    data object Empty : LocationsState
    data class Error(val message: String) : LocationsState
    data class Success(val data: List<Location>) : LocationsState
}