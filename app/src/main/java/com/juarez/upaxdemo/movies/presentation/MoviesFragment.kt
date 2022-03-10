package com.juarez.upaxdemo.movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juarez.upaxdemo.databinding.FragmentMoviesBinding
import com.juarez.upaxdemo.movies.data.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by activityViewModels()
    private val popularMoviesAdapter = MoviesAdapter { onItemClicked(it) }
    private val topMoviesAdapter = MoviesAdapter { onItemClicked(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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
        return binding.root
    }

    private fun shouldShowErrorOptions(visible: Boolean = false) {
        binding.btnRetry.isVisible = visible
        binding.txtMoviesError.isVisible = visible
    }

    private fun onItemClicked(movie: Movie) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}