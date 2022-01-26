package com.juarez.upaxdemo.data.datasource

import com.juarez.upaxdemo.api.MovieAPI
import com.juarez.upaxdemo.data.models.CustomResponse
import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.utils.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieAPI: MovieAPI) {
    suspend fun getPopularMovies(): CustomResponse<List<Movie>> {
        var movies = emptyList<Movie>()
        return try {
            val response = movieAPI.getPopularMovies()
            if (!response.isSuccessful) throw Exception(Constants.GENERAL_ERROR)
            response.body()?.let { movies = it.results }
            CustomResponse(isSuccess = true, data = movies)
        } catch (e: HttpException) {
            CustomResponse(message = e.message())
        } catch (e: IOException) {
            CustomResponse(message = Constants.CONNECTION_ERROR)
        }
    }

    suspend fun getTopRatedMovies(): CustomResponse<List<Movie>> {
        var movies = emptyList<Movie>()
        return try {
            val response = movieAPI.getTopRatedMovies()
            if (!response.isSuccessful) throw Exception(Constants.GENERAL_ERROR)
            response.body()?.let { movies = it.results }
            CustomResponse(isSuccess = true, data = movies)
        } catch (e: HttpException) {
            CustomResponse(message = e.message())
        } catch (e: IOException) {
            CustomResponse(message = Constants.CONNECTION_ERROR)
        }
    }

    suspend fun getMovieDetail(movieId: Int): CustomResponse<Movie> {
        var movie = Movie()
        return try {
            val response = movieAPI.getMovieDetail(movieId)
            if (!response.isSuccessful) throw Exception(Constants.GENERAL_ERROR)
            response.body()?.let { movie = it }
            CustomResponse(isSuccess = true, data = movie)
        } catch (e: Exception) {
            CustomResponse(message = e.message.toString())
        } catch (e: IOException) {
            CustomResponse(message = Constants.CONNECTION_ERROR)
        }
    }
}