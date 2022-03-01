package com.juarez.upaxdemo.photo.data

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.juarez.upaxdemo.utils.FirebaseResult
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ImagesRepository {
    fun uploadImage(uri: Uri, fileName: String): Flow<Resource<Boolean>>
    fun getImages(): Flow<FirebaseResult<MutableList<Photo>>>
    fun deleteImage(filename: String): Flow<FirebaseResult<Boolean>>
}

class ImagesRepositoryImp @Inject constructor(
    private val storageReference: StorageReference,
) : ImagesRepository {
    override fun uploadImage(uri: Uri, fileName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        storageReference.child("images/${fileName}").putFile(uri).await()
        emit(Resource.Success(true))
        emit(Resource.Loading(false))
    }

    override fun getImages(): Flow<FirebaseResult<MutableList<Photo>>> = flow {
        emit(FirebaseResult.Loading(true))
        val images = storageReference.child("images/").listAll().await()
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
        storageReference.child(("images/${filename}")).delete().await()
        emit(FirebaseResult.Success(true))
        emit(FirebaseResult.Loading(false))
    }

}