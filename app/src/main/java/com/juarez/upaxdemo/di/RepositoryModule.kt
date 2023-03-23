package com.juarez.upaxdemo.di

import com.juarez.upaxdemo.data.repositories.ImagesRepositoryImpl
import com.juarez.upaxdemo.data.repositories.LocationsRepositoryImpl
import com.juarez.upaxdemo.data.repositories.MovieRepositoryImpl
import com.juarez.upaxdemo.domain.repositories.ImagesRepository
import com.juarez.upaxdemo.domain.repositories.LocationsRepository
import com.juarez.upaxdemo.domain.repositories.MovieRepository
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