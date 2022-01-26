package com.juarez.upaxdemo.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.upaxdemo.data.models.Location
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: FirebaseRepository,
) : ViewModel() {

    private var _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun saveLocation(location: Location) = viewModelScope.launch {
        repository.saveLocation(location)
    }

    fun getLocations() {
        repository.getLocations().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _loading.value = resource.isLoading
                is Resource.Success -> resource.data.also { _locations.value = it }
                is Resource.Error -> {
                    Log.d("LocationsViewModel", resource.message)
                    _error.value = resource.message
                }

            }
        }.launchIn(viewModelScope)
    }

}