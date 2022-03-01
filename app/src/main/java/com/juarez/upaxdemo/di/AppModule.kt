package com.juarez.upaxdemo.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import com.juarez.upaxdemo.map.data.LocationsRepository
import com.juarez.upaxdemo.map.data.LocationsRepositoryImp
import com.juarez.upaxdemo.photo.data.ImagesRepository
import com.juarez.upaxdemo.photo.data.ImagesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLocationsRepository(collectionReference: CollectionReference): LocationsRepository {
        return LocationsRepositoryImp(collectionReference)
    }

    @Provides
    fun provideImagesRepository(storageReference: StorageReference): ImagesRepository {
        return ImagesRepositoryImp(storageReference)
    }
}