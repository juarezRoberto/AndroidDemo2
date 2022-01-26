package com.juarez.upaxdemo.data.repositories

import com.juarez.upaxdemo.data.datasource.MovieLocalDataSource
import com.juarez.upaxdemo.data.datasource.MovieRemoteDataSource
import com.juarez.upaxdemo.data.models.CustomResponse
import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.data.models.toEntity
import com.juarez.upaxdemo.data.models.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) {

    suspend fun getPopularMovies(): CustomResponse<List<Movie>> {
        val total = localDataSource.getTotalPopularMovies()
        if (total > 0) return CustomResponse(isSuccess = true)
        val response = remoteDataSource.getPopularMovies()
        response.data?.let { movies ->
            localDataSource.saveMovies(movies.map { it.toEntity("popular") })
        }
        return response
    }

    suspend fun getTopRatedMovies(): CustomResponse<List<Movie>> {
        val total = localDataSource.getTotalTopRatedMovies()
        if (total > 0) return CustomResponse(isSuccess = true)
        val response = remoteDataSource.getTopRatedMovies()
        response.data?.let { movies ->
            localDataSource.saveMovies(movies.map { it.toEntity("top") })
        }
        return response
    }

    suspend fun getMovieDetail(movieId: Int): CustomResponse<Movie> {
        val movie = localDataSource.getMovieById(movieId)
        movie?.let {
            return CustomResponse(isSuccess = true, data = it.toModel())
        }
        return remoteDataSource.getMovieDetail(movieId)
    }

    val popularMovies: Flow<List<Movie>> = localDataSource.popularMovies.map { movieEntities ->
        movieEntities.map { movieEntity -> movieEntity.toModel() }
    }

    val topRatedMovies: Flow<List<Movie>> = localDataSource.topRatedMovies.map { movieEntities ->
        movieEntities.map { movieEntity -> movieEntity.toModel() }
    }
}