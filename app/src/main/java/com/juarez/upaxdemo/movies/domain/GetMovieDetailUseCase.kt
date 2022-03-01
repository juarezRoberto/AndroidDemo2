package com.juarez.upaxdemo.movies.domain

import com.juarez.upaxdemo.movies.data.Movie
import com.juarez.upaxdemo.movies.data.MovieRepository
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(movieId: Int): Flow<Resource<Movie>> = repository.getMovieDetail(movieId)
}