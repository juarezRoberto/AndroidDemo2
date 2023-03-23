package com.juarez.upaxdemo.data.remote.models

import com.juarez.upaxdemo.domain.models.Movie

data class MovieResponse(val page: Int, val results: List<Movie>)