package com.juarez.androiddemo.data.repositories

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.juarez.androiddemo.domain.models.Photo
import com.juarez.androiddemo.domain.repositories.ImagesRepository
import com.juarez.androiddemo.utils.FirebaseResult
import com.juarez.androiddemo.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val storageReference: StorageReference,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ImagesRepository {

    override fun uploadImage(uri: Uri, fileName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        withContext(defaultDispatcher) {
            storageReference.child("images/${fileName}").putFile(uri).await()
        }
        emit(Resource.Success(true))
        emit(Resource.Loading(false))
    }

    override fun getImages(): Flow<FirebaseResult<MutableList<Photo>>> = flow {
        emit(FirebaseResult.Loading(true))
        val images = withContext(defaultDispatcher) {
            storageReference.child("images/").listAll().await()
        }
        val imageUrls = mutableListOf<Photo>()
        images.items.forEach { img ->
            val url = img.downloadUrl.await()
            val metadata = img.metadata.await()
            val size = metadata.sizeBytes
            imageUrls.add(Photo(url.toString(), img.name, size.toString()))
        }
        emit(FirebaseResult.Success(imageUrls))
        emit(FirebaseResult.Loading(false))
    }

    override fun deleteImage(filename: String): Flow<FirebaseResult<Boolean>> = flow {
        emit(FirebaseResult.Loading(true))
        withContext(defaultDispatcher) {
            storageReference.child(("images/${filename}")).delete().await()
        }
        emit(FirebaseResult.Success(true))
        emit(FirebaseResult.Loading(false))
    }

}