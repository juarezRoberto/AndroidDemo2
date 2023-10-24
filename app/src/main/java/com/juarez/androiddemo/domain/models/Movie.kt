package com.juarez.androiddemo.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val poster_path: String = "",
    val overview: String = "",
)

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val category: String,
    val title: String,
    val poster_path: String,
    val overview: String,
)

fun Movie.toEntity(movieCategory: String): MovieEntity {
    return MovieEntity(
        movieId = id,
        category = movieCategory,
        title = title,
        poster_path = poster_path,
        overview = overview
    )
}

fun MovieEntity.toModel(): Movie {
    return Movie(
        id = movieId,
        title = title,
        poster_path = poster_path,
        overview = overview
    )
}