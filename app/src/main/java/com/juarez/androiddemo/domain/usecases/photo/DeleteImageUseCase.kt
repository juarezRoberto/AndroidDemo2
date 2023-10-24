package com.juarez.androiddemo.domain.usecases.photo

import com.juarez.androiddemo.domain.repositories.ImagesRepository
import com.juarez.androiddemo.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteImageUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(filename: String): Flow<FirebaseResult<Boolean>> {
        return repository.deleteImage(filename)
    }
}