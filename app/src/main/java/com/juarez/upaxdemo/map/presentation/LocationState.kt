package com.juarez.upaxdemo.map.presentation

import com.juarez.upaxdemo.map.data.Location

sealed class LocationsState {
    data class Loading(val isLoading: Boolean) : LocationsState()
    object Empty : LocationsState()
    data class Error(val message: String) : LocationsState()
    data class Success(val data: List<Location>) : LocationsState()
}