package com.juarez.androiddemo.domain.repositories

import android.net.Uri
import com.juarez.androiddemo.domain.models.Photo
import com.juarez.androiddemo.utils.FirebaseResult
import com.juarez.androiddemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun uploadImage(uri: Uri, fileName: String): Flow<Resource<Boolean>>
    fun getImages(): Flow<FirebaseResult<MutableList<Photo>>>
    fun deleteImage(filename: String): Flow<FirebaseResult<Boolean>>
}

