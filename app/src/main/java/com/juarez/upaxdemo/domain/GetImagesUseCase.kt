package com.juarez.upaxdemo.domain

import com.juarez.upaxdemo.data.models.Photo
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.data.repositories.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository: FirebaseRepository) {
    operator fun invoke(): Flow<FirebaseResult<MutableList<Photo>>> = repository.getImages()
}