package com.juarez.upaxdemo.repositories

import android.util.Log
import com.juarez.upaxdemo.api.WebService
import com.juarez.upaxdemo.models.CustomResponse
import com.juarez.upaxdemo.models.Movie
import com.juarez.upaxdemo.utils.Constants
import kotlinx.coroutines.delay

class MovieRepository {

    suspend fun getPopularMovies(): CustomResponse<List<Movie>> {
        var customResponse: CustomResponse<List<Movie>>

        try {
            delay(1000)
            val response = WebService.service().getPopularMovies()
            Log.d("res", response.body().toString())
            customResponse = if (response.isSuccessful) {
                CustomResponse(true, response.body()?.results, null)
            } else {
                CustomResponse(false, null, Constants.GENERAL_ERROR)
            }
        } catch (e: Exception) {
            customResponse = CustomResponse(false, null, e.message.toString())
        }

        return customResponse
    }

    suspend fun getTopRatedMovies(
        callback: (isSuccess: Boolean, data: List<Movie>?, message: String?) -> Unit
    ) {
        try {
            delay(1000)
            val response = WebService.service().getTopRatedMovies()
            if (response.isSuccessful) {
                callback.invoke(true, response.body()?.results, null)
            } else {
                callback.invoke(false, null, Constants.GENERAL_ERROR)
            }
        } catch (e: Exception) {
            callback.invoke(false, null, e.message.toString())
        }
    }

    suspend fun getMovieDetail(
        movieId: Int,
        callback: (isSuccess: Boolean, data: Movie?, message: String?) -> Unit
    ) {
        try {
            delay(1000)
            val response = WebService.service().getMovieDetail(movieId)
            if (response.isSuccessful) {
                callback.invoke(true, response.body(), null)
            } else {
                callback.invoke(false, null, Constants.GENERAL_ERROR)
            }
        } catch (e: Exception) {
            callback.invoke(false, null, e.message.toString())
        }
    }
}