package com.juarez.upaxdemo.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.juarez.upaxdemo.databinding.FragmentMovieDetailBinding
import com.juarez.upaxdemo.utils.loadPosterImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private val viewModel: MoviesViewModel by activityViewModels()
    private val args: MovieDetailFragmentArgs by navArgs()
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        shouldShowErrorOptions()
        binding.btnDetailRetry.setOnClickListener {
            shouldShowErrorOptions()
            viewModel.getMovieDetail(args.movieId)
        }

        viewModel.getMovieDetail(args.movieId)
        viewModel.movie.observe(viewLifecycleOwner, {
            with(binding) {
                imgPoster.loadPosterImage(it.poster_path)
                txtName.text = it.title
                txtOverview.text = it.overview
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressMovieDetail.isVisible = it
        })
        viewModel.error.observe(viewLifecycleOwner, {
            shouldShowErrorOptions(true)
        })
        return binding.root
    }

    private fun shouldShowErrorOptions(visible: Boolean = false) {
        binding.btnDetailRetry.isVisible = visible
        binding.txtDetailError.isVisible = visible
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}