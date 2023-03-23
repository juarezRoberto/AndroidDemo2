package com.juarez.upaxdemo.domain.repositories

import com.juarez.upaxdemo.domain.models.Movie
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAllPopularMovies(): Flow<Resource<List<Movie>>>
    fun getAllTopRatedMovies(): Flow<Resource<List<Movie>>>
    fun getMovieDetail(movieId: Int): Flow<Resource<Movie>>
    val popularMovies: Flow<List<Movie>>
    val topRatedMovies: Flow<List<Movie>>
}