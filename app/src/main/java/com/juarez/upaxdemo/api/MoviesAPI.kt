package com.juarez.upaxdemo.api

import com.juarez.upaxdemo.movies.data.Movie
import com.juarez.upaxdemo.movies.data.MovieResponse
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