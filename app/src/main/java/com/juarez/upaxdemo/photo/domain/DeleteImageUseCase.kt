package com.juarez.upaxdemo.photo.domain

import com.juarez.upaxdemo.photo.data.ImagesRepository
import com.juarez.upaxdemo.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(filename: String): Flow<FirebaseResult<Boolean>> {
        return repository.deleteImage(filename)
    }
}