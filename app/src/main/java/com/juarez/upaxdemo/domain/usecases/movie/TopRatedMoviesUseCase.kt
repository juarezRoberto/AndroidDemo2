package com.juarez.upaxdemo.domain.usecases.movie

import com.juarez.upaxdemo.domain.models.Movie
import com.juarez.upaxdemo.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.topRatedMovies
}