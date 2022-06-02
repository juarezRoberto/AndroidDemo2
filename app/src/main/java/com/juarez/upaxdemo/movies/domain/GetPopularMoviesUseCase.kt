package com.juarez.upaxdemo.movies.domain

import com.juarez.upaxdemo.movies.data.Movie
import com.juarez.upaxdemo.movies.data.MovieRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = repository.getAllPopularMovies()
}