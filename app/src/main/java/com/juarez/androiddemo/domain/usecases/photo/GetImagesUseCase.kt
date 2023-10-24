package com.juarez.androiddemo.domain.usecases.photo

import com.juarez.androiddemo.domain.models.Photo
import com.juarez.androiddemo.domain.repositories.ImagesRepository
import com.juarez.androiddemo.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(): Flow<FirebaseResult<MutableList<Photo>>> = repository.getImages()
}