package com.juarez.upaxdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.upaxdemo.models.Movie
import com.juarez.upaxdemo.repositories.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    // observables
    val popularMovies = MutableLiveData<List<Movie>>()
    val movie = MutableLiveData<Movie>()
    val topMovies = MutableLiveData<List<Movie>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    init {
        getTopMovies()
    }

    fun getMovies() = viewModelScope.launch {
        loading.value = true
        val response = repository.getPopularMovies()
        if (response.isSuccess) popularMovies.value = response.data!!
        else error.value = response.message!!
        loading.value = false
    }

    fun getTopMovies() = viewModelScope.launch {
        loading.value = true
        repository.getTopRatedMovies { isSuccess, data, message ->
            if (isSuccess) topMovies.value = data
            else error.value = message
        }
        loading.value = false
    }

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        loading.value = true
        repository.getMovieDetail(movieId) { isSuccess, data, message ->
            if (isSuccess) movie.value = data
            else error.value = message
        }
        loading.value = false
    }
}