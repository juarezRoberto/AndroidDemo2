package com.juarez.upaxdemo.domain.usecases.movie

import com.juarez.upaxdemo.domain.models.Movie
import com.juarez.upaxdemo.domain.repositories.MovieRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = repository.getAllTopRatedMovies()
}