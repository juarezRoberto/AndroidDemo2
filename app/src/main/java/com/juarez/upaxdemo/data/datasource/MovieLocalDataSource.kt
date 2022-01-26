package com.juarez.upaxdemo.data.datasource

import com.juarez.upaxdemo.data.db.MovieDao
import com.juarez.upaxdemo.data.models.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    val popularMovies: Flow<List<MovieEntity>> = movieDao.getPopularMovies()

    val topRatedMovies: Flow<List<MovieEntity>> = movieDao.getTopRatedMovies()

    suspend fun saveMovies(moviesEntities: List<MovieEntity>) {
        movieDao.saveMovies(*moviesEntities.toTypedArray())
    }

    suspend fun getTotalPopularMovies() = movieDao.getTotalPopularMovies()

    suspend fun getTotalTopRatedMovies() = movieDao.getTotalTopRatedMovies()

    suspend fun getMovieById(movieId: Int) = movieDao.getMovieById(movieId)

    private suspend fun deleteMovies() = movieDao.deleteAllMovies()
}