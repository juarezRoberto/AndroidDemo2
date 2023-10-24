package com.juarez.androiddemo.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.domain.usecases.movie.*
import com.juarez.androiddemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    popularMoviesUseCase: PopularMoviesUseCase,
    topRatedMoviesUseCase: TopRatedMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getPopularMoviesUseCaseUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
) : ViewModel() {
    private var _movie = MutableStateFlow(Movie())
    val movie = _movie.asStateFlow()
    private var _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    init {
        getTopMovies()
        getPopularMovies()
    }

    val popularMovies: Flow<List<Movie>> = popularMoviesUseCase()

    val topMovies: Flow<List<Movie>> = topRatedMoviesUseCase()

    fun getPopularMovies() {
        _error.value = ""
        getPopularMoviesUseCaseUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _loading.value = result.isLoading
                is Resource.Success -> Unit
                is Resource.Error -> _error.value = result.message
            }
        }.launchIn(viewModelScope)
    }

    fun getTopMovies() {
        _error.value = ""
        getTopRatedMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _loading.value = result.isLoading
                is Resource.Success -> Unit
                is Resource.Error -> _error.value = result.message
            }
        }.launchIn(viewModelScope)
    }

    fun getMovieDetail(movieId: Int) {
        _error.value = ""
        getMovieDetailUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Loading -> _loading.value = result.isLoading
                is Resource.Success -> result.data.also { _movie.value = it }
                is Resource.Error -> _error.value = result.message
            }
        }.launchIn(viewModelScope)
    }
}