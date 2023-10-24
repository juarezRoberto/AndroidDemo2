package com.juarez.androiddemo.data.remote.datasources

import com.juarez.androiddemo.common.Constants
import com.juarez.androiddemo.data.remote.api.MovieAPI
import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.utils.NetworkResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieAPI: MovieAPI) {
    suspend fun getPopularMoviesAPI(): NetworkResponse<List<Movie>> {
        return try {
            val response = movieAPI.getPopularMovies()
            if (!response.isSuccessful || response.body() == null) throw Exception(Constants.GENERAL_ERROR)
            NetworkResponse.Success(response.body()!!.results)
        } catch (e: HttpException) {
            NetworkResponse.Error(e.localizedMessage ?: "Unexpected error")
        } catch (e: IOException) {
            NetworkResponse.Error(Constants.CONNECTION_ERROR)
        }
    }

    suspend fun getTopRatedMovies(): NetworkResponse<List<Movie>> {
        return try {
            val response = movieAPI.getTopRatedMovies()
            if (!response.isSuccessful || response.body() == null) throw Exception(Constants.GENERAL_ERROR)
            NetworkResponse.Success(response.body()!!.results)
        } catch (e: HttpException) {
            NetworkResponse.Error(e.localizedMessage ?: "Unexpected error")
        } catch (e: IOException) {
            NetworkResponse.Error(Constants.CONNECTION_ERROR)
        }
    }

    suspend fun getMovieDetail(movieId: Int): NetworkResponse<Movie> {
        return try {
            val response = movieAPI.getMovieDetail(movieId)
            if (!response.isSuccessful || response.body() == null) throw Exception(Constants.GENERAL_ERROR)
            NetworkResponse.Success(response.body()!!)
        } catch (e: HttpException) {
            NetworkResponse.Error(e.localizedMessage ?: "Unexpected error")
        } catch (e: IOException) {
            NetworkResponse.Error(Constants.CONNECTION_ERROR)
        }
    }
}