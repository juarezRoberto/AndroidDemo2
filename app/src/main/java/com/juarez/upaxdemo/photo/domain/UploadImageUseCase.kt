package com.juarez.upaxdemo.photo.domain

import android.net.Uri
import com.juarez.upaxdemo.photo.data.ImagesRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: ImagesRepository) {
    operator fun invoke(uri: Uri, fileName: String): Flow<Resource<Boolean>> {
        return repository.uploadImage(uri, fileName)
    }
}