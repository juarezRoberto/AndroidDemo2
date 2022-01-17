package com.juarez.upaxdemo.models

data class Movie(
    val id: Int,
    val name: String,
    val title: String,
    val poster_path: String,
    val overview: String
)

data class MovieResponse(val page: Int, val results: List<Movie>)