package com.juarez.upaxdemo.photo.domain

import com.juarez.upaxdemo.photo.data.ImagesRepository
import com.juarez.upaxdemo.photo.data.Photo
import com.juarez.upaxdemo.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(): Flow<FirebaseResult<MutableList<Photo>>> = repository.getImages()
}