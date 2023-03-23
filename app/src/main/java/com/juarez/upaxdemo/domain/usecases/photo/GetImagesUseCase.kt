package com.juarez.upaxdemo.domain.usecases.photo

import com.juarez.upaxdemo.domain.models.Photo
import com.juarez.upaxdemo.domain.repositories.ImagesRepository
import com.juarez.upaxdemo.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(): Flow<FirebaseResult<MutableList<Photo>>> = repository.getImages()
}