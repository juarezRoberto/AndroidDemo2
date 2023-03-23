package com.juarez.upaxdemo.domain.usecases.map

import com.juarez.upaxdemo.domain.models.Location
import com.juarez.upaxdemo.domain.repositories.LocationsRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationsRepository) {
    operator fun invoke(): Flow<Resource<List<Location>>> = repository.getLocations()
}