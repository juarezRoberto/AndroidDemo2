package com.juarez.upaxdemo.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObjects
import com.juarez.upaxdemo.domain.models.Location
import com.juarez.upaxdemo.domain.repositories.LocationsRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationsCollection: CollectionReference,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocationsRepository {

    override suspend fun saveLocation(location: Location) {
        withContext(defaultDispatcher) { locationsCollection.add(location).await() }
    }

    override fun getLocations(): Flow<Resource<List<Location>>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapshot = withContext(defaultDispatcher) { locationsCollection.get().await() }
            val locations = snapshot.toObjects<Location>()
            emit(Resource.Success(locations))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        emit(Resource.Loading(false))
    }

}