package com.juarez.androiddemo.ui.movie

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juarez.androiddemo.databinding.FragmentMoviesBinding
import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {
    private val viewModel: MoviesViewModel by activityViewModels()
    private val popularMoviesAdapter = MoviesAdapter { onItemClicked(it) }
    private val topMoviesAdapter = MoviesAdapter { onItemClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init views
        binding.recyclerPopularMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMoviesAdapter
        }
        binding.recyclerTopMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topMoviesAdapter
        }
        shouldShowErrorOptions()
        binding.btnRetry.setOnClickListener {
            shouldShowErrorOptions()
            viewModel.getPopularMovies()
            viewModel.getTopMovies()
        }
        // observers
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.popularMovies.collect { popularMoviesAdapter.submitList(it) }
                }
                launch {
                    viewModel.topMovies.collect { topMoviesAdapter.submitList(it) }
                }
                launch {
                    viewModel.loading.collect { binding.progressBarMovies.isVisible = it }
                }
                launch {
                    viewModel.error.collect {
                        if (it.isNotEmpty()) {
                            shouldShowErrorOptions(true)
                            binding.txtMoviesError.text = it
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowErrorOptions(visible: Boolean = false) {
        binding.btnRetry.isVisible = visible
        binding.txtMoviesError.isVisible = visible
    }

    private fun onItemClicked(movie: Movie) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie.id)
        findNavController().navigate(action)
    }
}