package com.juarez.upaxdemo.data.repositories

import com.juarez.upaxdemo.data.datasource.MovieLocalDataSource
import com.juarez.upaxdemo.data.datasource.MovieRemoteDataSource
import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.data.models.toEntity
import com.juarez.upaxdemo.data.models.toModel
import com.juarez.upaxdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
) {

    fun getAllPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(true))
        val total = localDataSource.getTotalPopularMovies()
        if (total < 1) {
            val response = remoteDataSource.getPopularMoviesAPI()
            if (response.isSuccess) {
                localDataSource.saveMovies(response.data!!.map { it.toEntity("popular") })
            } else emit(Resource.Error(response.message))
        }
        emit(Resource.Loading(false))
    }

    fun getAllTopRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(true))
        val total = localDataSource.getTotalTopRatedMovies()
        if (total < 1) {
            val response = remoteDataSource.getTopRatedMovies()
            if (response.isSuccess) {
                localDataSource.saveMovies(response.data!!.map { it.toEntity("top") })
            } else emit(Resource.Error(response.message))
        }
        emit(Resource.Loading(false))
    }

    fun getMovieDetail(movieId: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading(true))
        val movie = localDataSource.getMovieById(movieId)
        if (movie != null) {
            emit(Resource.Success(movie.toModel()))
        } else {
            val response = remoteDataSource.getMovieDetail(movieId)
            if (response.isSuccess) emit(Resource.Success(response.data!!))
            else emit(Resource.Error(response.message))
        }
        emit(Resource.Loading(false))
    }

    val popularMovies: Flow<List<Movie>> = localDataSource.popularMovies.map { movieEntities ->
        movieEntities.map { movieEntity -> movieEntity.toModel() }
    }

    val topRatedMovies: Flow<List<Movie>> = localDataSource.topRatedMovies.map { movieEntities ->
        movieEntities.map { movieEntity -> movieEntity.toModel() }
    }
}