package com.juarez.upaxdemo.data.datasource

import com.juarez.upaxdemo.api.MovieAPI
import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.data.models.Response
import com.juarez.upaxdemo.utils.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieAPI: MovieAPI) {
    suspend fun getPopularMoviesAPI(): Response<List<Movie>> {
        return try {
            val response = movieAPI.getPopularMovies()
            if (!response.isSuccessful || response.body() == null) throw Exception(Constants.GENERAL_ERROR)
            Response(isSuccess = true, data = response.body()!!.results)
        } catch (e: HttpException) {
            Response(message = e.message())
        } catch (e: IOException) {
            Response(message = Constants.CONNECTION_ERROR)
        }
    }

    suspend fun getTopRatedMovies(): Response<List<Movie>> {
        return try {
            val response = movieAPI.getTopRatedMovies()
            if (!response.isSuccessful || response.body() == null) throw Exception(Constants.GENERAL_ERROR)
            Response(isSuccess = true, data = response.body()!!.results)
        } catch (e: HttpException) {
            Response(message = e.message())
        } catch (e: IOException) {
            Response(message = Constants.CONNECTION_ERROR)
        }
    }

    suspend fun getMovieDetail(movieId: Int): Response<Movie> {
        return try {
            val response = movieAPI.getMovieDetail(movieId)
            if (!response.isSuccessful || response.body() == null) throw Exception(Constants.GENERAL_ERROR)
            Response(isSuccess = true, data = response.body())
        } catch (e: Exception) {
            Response(message = e.message.toString())
        } catch (e: IOException) {
            Response(message = Constants.CONNECTION_ERROR)
        }
    }
}