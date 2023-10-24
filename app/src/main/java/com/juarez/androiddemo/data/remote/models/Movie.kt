package com.juarez.androiddemo.data.remote.models

import com.juarez.androiddemo.domain.models.Movie

data class MovieResponse(val page: Int, val results: List<Movie>)