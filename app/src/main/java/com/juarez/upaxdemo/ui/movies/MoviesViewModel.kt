package com.juarez.upaxdemo.ui.movies

import androidx.lifecycle.*
import com.juarez.upaxdemo.data.models.Movie
import com.juarez.upaxdemo.domain.*
import com.juarez.upaxdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    popularMoviesUseCase: PopularMoviesUseCase,
    topRatedMoviesUseCase: TopRatedMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getPopularMoviesUseCaseUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
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

    val popularMovies: LiveData<List<Movie>> = popularMoviesUseCase().asLiveData()

    val topMovies: LiveData<List<Movie>> = topRatedMoviesUseCase().asLiveData()

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