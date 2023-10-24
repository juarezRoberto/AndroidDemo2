package com.juarez.androiddemo.di

import com.juarez.androiddemo.data.repositories.ImagesRepositoryImpl
import com.juarez.androiddemo.data.repositories.LocationsRepositoryImpl
import com.juarez.androiddemo.data.repositories.MovieRepositoryImpl
import com.juarez.androiddemo.domain.repositories.ImagesRepository
import com.juarez.androiddemo.domain.repositories.LocationsRepository
import com.juarez.androiddemo.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLocationsRepository(locationsRepositoryImpl: LocationsRepositoryImpl): LocationsRepository

    @Binds
    abstract fun bindImagesRepository(imagesRepositoryImpl: ImagesRepositoryImpl): ImagesRepository

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}