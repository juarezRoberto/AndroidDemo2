package com.juarez.upaxdemo.ui.map

import com.juarez.upaxdemo.data.models.Location

sealed class LocationsState {
    data class Loading(val isLoading:Boolean) : LocationsState()
    object Empty : LocationsState()
    data class Error(val message: String) : LocationsState()
    data class Success(val data: List<Location>) : LocationsState()
}