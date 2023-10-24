package com.juarez.androiddemo.domain.usecases.map

import com.juarez.androiddemo.domain.models.Location
import com.juarez.androiddemo.domain.repositories.LocationsRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(private val repository: LocationsRepository) {
    suspend operator fun invoke(location: Location) = repository.saveLocation(location)
}
