package com.juarez.upaxdemo.movies.domain

import com.juarez.upaxdemo.movies.data.Movie
import com.juarez.upaxdemo.movies.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.topRatedMovies
}