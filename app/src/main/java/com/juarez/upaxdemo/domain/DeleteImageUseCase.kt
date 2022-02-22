package com.juarez.upaxdemo.domain

import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.data.repositories.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(private val repository: FirebaseRepository) {
    operator fun invoke(filename: String): Flow<FirebaseResult<Boolean>> {
        return repository.deleteImage(filename)
    }
}