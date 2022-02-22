package com.juarez.upaxdemo.domain

import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.data.repositories.MovieRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = repository.getAllTopRatedMovies()
}