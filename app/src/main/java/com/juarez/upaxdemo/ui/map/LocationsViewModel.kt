package com.juarez.upaxdemo.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.upaxdemo.data.models.Location
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: FirebaseRepository,
) : ViewModel() {

    private val _locationsState = MutableStateFlow<LocationsState>(LocationsState.Empty)
    val locationState: StateFlow<LocationsState> = _locationsState

    fun saveLocation(location: Location) = viewModelScope.launch {
        _locationsState.value = LocationsState.Loading(true)
        repository.saveLocation(location)
        _locationsState.value = LocationsState.Loading(false)
        getLocations()
    }

    fun getLocations() {
        repository.getLocations().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _locationsState.value =
                    LocationsState.Loading(resource.isLoading)
                is Resource.Success -> _locationsState.value = LocationsState.Success(resource.data)
                is Resource.Error -> _locationsState.value = LocationsState.Error(resource.message)
            }
        }.launchIn(viewModelScope)
    }
}