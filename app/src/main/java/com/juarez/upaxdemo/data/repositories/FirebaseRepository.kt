package com.juarez.upaxdemo.data.repositories

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.juarez.upaxdemo.data.models.Location
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val locationsCollectionRef = firestore.collection("locations")

    suspend fun saveLocation(location: Location) {
        try {
            locationsCollectionRef.add(location).await()
            Log.d("LocationsViewModel", "success")
        } catch (e: FirebaseException) {
            Log.d("LocationsViewModel", "error" + e.localizedMessage)
        }
    }

    fun getLocations(): Flow<Resource<List<Location>>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapshot = locationsCollectionRef.get().await()
            val locations = snapshot.toObjects<Location>()
            emit(Resource.Success(locations))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        emit(Resource.Loading(false))
    }
}