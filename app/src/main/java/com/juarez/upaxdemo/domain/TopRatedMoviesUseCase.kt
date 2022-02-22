package com.juarez.upaxdemo.domain

import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.data.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.topRatedMovies
}