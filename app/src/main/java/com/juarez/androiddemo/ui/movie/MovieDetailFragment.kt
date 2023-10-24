package com.juarez.androiddemo.ui.movie

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.juarez.androiddemo.databinding.FragmentMovieDetailBinding
import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.utils.BaseFragment
import com.juarez.androiddemo.utils.loadPosterImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    private val viewModel: MoviesViewModel by activityViewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shouldShowErrorOptions()
        binding.btnDetailRetry.setOnClickListener {
            shouldShowErrorOptions()
            viewModel.getMovieDetail(args.movieId)
        }

        viewModel.getMovieDetail(args.movieId)
        
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movie.collect { updateUI(it) }
                }
                launch {
                    viewModel.loading.collect { binding.progressMovieDetail.isVisible = it }
                }
                launch {
                    viewModel.error.collect {
                        if (it.isNotEmpty()) {
                            shouldShowErrorOptions(true)
                            binding.txtDetailError.text = it
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(movie: Movie) {
        with(binding) {
            imgPoster.loadPosterImage(movie.poster_path)
            txtName.text = movie.title
            txtOverview.text = movie.overview
        }
    }

    private fun shouldShowErrorOptions(visible: Boolean = false) {
        binding.btnDetailRetry.isVisible = visible
        binding.txtDetailError.isVisible = visible
    }

}