package com.juarez.androiddemo.domain.usecases.movie

import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.topRatedMovies
}