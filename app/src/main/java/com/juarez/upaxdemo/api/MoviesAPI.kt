package com.juarez.upaxdemo.api

import com.juarez.upaxdemo.models.Movie
import com.juarez.upaxdemo.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): Response<Movie>
}