package com.juarez.upaxdemo.ui.movies

import androidx.lifecycle.*
import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.data.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private var _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        getTopMovies()
        getPopularMovies()
    }

    val popularMovies: LiveData<List<Movie>> = repository.popularMovies.asLiveData()

    val topMovies: LiveData<List<Movie>> = repository.topRatedMovies.asLiveData()

    fun getPopularMovies() = viewModelScope.launch {
        _error.value = ""
        _loading.value = true
        val response = repository.getPopularMovies()
        if (!response.isSuccess) _error.value = response.message!!
        _loading.value = false
    }

    fun getTopMovies() = viewModelScope.launch {
        _error.value = ""
        _loading.value = true
        val response = repository.getTopRatedMovies()
        if (!response.isSuccess) _error.value = response.message!!
        _loading.value = false
    }

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _error.value = ""
        _loading.value = true
        val response = repository.getMovieDetail(movieId)
        if (response.isSuccess) _movie.value = response.data!!
        else _error.value = response.message!!
        _loading.value = false
    }
}