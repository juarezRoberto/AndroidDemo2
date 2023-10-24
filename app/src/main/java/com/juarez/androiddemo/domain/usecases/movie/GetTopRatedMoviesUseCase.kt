package com.juarez.androiddemo.domain.usecases.movie

import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.domain.repositories.MovieRepository
import com.juarez.androiddemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = repository.getAllTopRatedMovies()
}