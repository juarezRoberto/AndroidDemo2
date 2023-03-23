package com.juarez.upaxdemo.domain.usecases.photo

import com.juarez.upaxdemo.domain.repositories.ImagesRepository
import com.juarez.upaxdemo.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteImageUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(filename: String): Flow<FirebaseResult<Boolean>> {
        return repository.deleteImage(filename)
    }
}