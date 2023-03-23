package com.juarez.upaxdemo.domain.repositories

import com.juarez.upaxdemo.domain.models.Location
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    suspend fun saveLocation(location: Location)
    fun getLocations(): Flow<Resource<List<Location>>>
}