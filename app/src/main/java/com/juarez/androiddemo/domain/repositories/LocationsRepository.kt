package com.juarez.androiddemo.domain.repositories

import com.juarez.androiddemo.domain.models.Location
import com.juarez.androiddemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    suspend fun saveLocation(location: Location)
    fun getLocations(): Flow<Resource<List<Location>>>
}