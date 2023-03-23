package com.juarez.upaxdemo.domain.repositories

import android.net.Uri
import com.juarez.upaxdemo.domain.models.Photo
import com.juarez.upaxdemo.utils.FirebaseResult
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun uploadImage(uri: Uri, fileName: String): Flow<Resource<Boolean>>
    fun getImages(): Flow<FirebaseResult<MutableList<Photo>>>
    fun deleteImage(filename: String): Flow<FirebaseResult<Boolean>>
}

