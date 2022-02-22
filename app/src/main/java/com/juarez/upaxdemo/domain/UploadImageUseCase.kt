package com.juarez.upaxdemo.domain

import android.net.Uri
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: FirebaseRepository) {
    operator fun invoke(uri: Uri, fileName: String): Flow<Resource<Boolean>> {
        return repository.uploadImage(uri, fileName)
    }
}