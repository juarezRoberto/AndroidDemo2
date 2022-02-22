package com.juarez.upaxdemo.domain

import com.juarez.upaxdemo.data.models.Location
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: FirebaseRepository) {
    operator fun invoke(): Flow<Resource<List<Location>>> = repository.getLocations()
}