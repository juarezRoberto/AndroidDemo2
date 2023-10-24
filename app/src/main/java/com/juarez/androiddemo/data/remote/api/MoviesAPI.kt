package com.juarez.androiddemo.data.remote.api

import com.juarez.androiddemo.data.remote.models.MovieResponse
import com.juarez.androiddemo.domain.models.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<Movie>
}