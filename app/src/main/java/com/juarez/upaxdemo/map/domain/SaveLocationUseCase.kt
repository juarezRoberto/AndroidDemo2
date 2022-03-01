package com.juarez.upaxdemo.map.domain

import com.juarez.upaxdemo.map.data.Location
import com.juarez.upaxdemo.map.data.LocationsRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(private val repository: LocationsRepository) {
    suspend operator fun invoke(location: Location) = repository.saveLocation(location)
}
