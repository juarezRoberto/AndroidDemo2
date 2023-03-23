package com.juarez.upaxdemo.domain.usecases.photo

import android.net.Uri
import com.juarez.upaxdemo.domain.repositories.ImagesRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadImageUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(uri: Uri, fileName: String): Flow<Resource<Boolean>> {
        return repository.uploadImage(uri, fileName)
    }
}