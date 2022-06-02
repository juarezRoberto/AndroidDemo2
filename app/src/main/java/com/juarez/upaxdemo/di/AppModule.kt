package com.juarez.upaxdemo.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import com.juarez.upaxdemo.map.data.LocationsRepository
import com.juarez.upaxdemo.map.data.LocationsRepositoryImp
import com.juarez.upaxdemo.movies.data.MovieLocalDataSource
import com.juarez.upaxdemo.movies.data.MovieRemoteDataSource
import com.juarez.upaxdemo.movies.data.MovieRepository
import com.juarez.upaxdemo.movies.data.MovieRepositoryImpl
import com.juarez.upaxdemo.photo.data.ImagesRepository
import com.juarez.upaxdemo.photo.data.ImagesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLocationsRepository(collectionReference: CollectionReference): LocationsRepository {
        return LocationsRepositoryImp(collectionReference, Dispatchers.IO)
    }

    @Provides
    fun provideImagesRepository(storageReference: StorageReference): ImagesRepository {
        return ImagesRepositoryImp(storageReference, Dispatchers.IO)
    }

    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        localDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, localDataSource, Dispatchers.IO)
    }
}