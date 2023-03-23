package com.juarez.upaxdemo.domain.usecases.map

import com.juarez.upaxdemo.domain.models.Location
import com.juarez.upaxdemo.domain.repositories.LocationsRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(private val repository: LocationsRepository) {
    suspend operator fun invoke(location: Location) = repository.saveLocation(location)
}
