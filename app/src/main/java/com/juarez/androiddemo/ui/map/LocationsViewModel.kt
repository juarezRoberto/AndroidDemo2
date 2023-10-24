package com.juarez.androiddemo.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.androiddemo.domain.models.Location
import com.juarez.androiddemo.domain.usecases.map.GetLocationsUseCase
import com.juarez.androiddemo.domain.usecases.map.SaveLocationUseCase
import com.juarez.androiddemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
) : ViewModel() {

    private val _locationsState = MutableStateFlow<LocationsState>(LocationsState.Empty)
    val locationState: StateFlow<LocationsState> = _locationsState

    fun saveLocation(location: Location) = viewModelScope.launch {
        _locationsState.value = LocationsState.Loading(true)
        saveLocationUseCase(location)
        _locationsState.value = LocationsState.Loading(false)
        getLocations()
    }

    fun getLocations() {
        getLocationsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _locationsState.value =
                    LocationsState.Loading(resource.isLoading)
                is Resource.Success -> _locationsState.value = LocationsState.Success(resource.data)
                is Resource.Error -> _locationsState.value = LocationsState.Error(resource.message)
            }
        }.launchIn(viewModelScope)
    }
}