package com.juarez.upaxdemo.domain

import com.juarez.upaxdemo.data.models.Location
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend operator fun invoke(location: Location) = repository.saveLocation(location)
}
