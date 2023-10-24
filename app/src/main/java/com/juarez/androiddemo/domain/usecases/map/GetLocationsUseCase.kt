package com.juarez.androiddemo.domain.usecases.map

import com.juarez.androiddemo.domain.models.Location
import com.juarez.androiddemo.domain.repositories.LocationsRepository
import com.juarez.androiddemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationsRepository) {
    operator fun invoke(): Flow<Resource<List<Location>>> = repository.getLocations()
}