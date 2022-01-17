package com.juarez.upaxdemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.juarez.upaxdemo.adapters.MoviesAdapter
import com.juarez.upaxdemo.databinding.FragmentMoviesBinding
import com.juarez.upaxdemo.models.Movie
import com.juarez.upaxdemo.repositories.MovieRepository
import com.juarez.upaxdemo.viewmodels.MoviesViewModel
import com.juarez.upaxdemo.viewmodels.MoviesViewModelFactory

class MoviesFragment : Fragment() {
    private val TAG: String = "MoviesFragment"
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MoviesViewModel
    private val popularMoviesAdapter = MoviesAdapter(arrayListOf()) { onItemClicked(it) }
    private val topMoviesAdapter = MoviesAdapter(arrayListOf()) { onItemClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val provider = MoviesViewModelFactory(MovieRepository())
        viewModel = ViewModelProvider(this, provider).get(MoviesViewModel::class.java)
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
        binding.btnRetry.isVisible = false
        binding.btnRetry.setOnClickListener {
            viewModel.getMovies()
            viewModel.getTopMovies()
        }
        viewModel.getMovies()
        // observers
        viewModel.popularMovies.observe(viewLifecycleOwner, {
            popularMoviesAdapter.updateData(it)
        })
        viewModel.topMovies.observe(viewLifecycleOwner, {
            topMoviesAdapter.updateData(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBarMovies.isVisible = it
        })
        viewModel.error.observe(viewLifecycleOwner, {
            binding.btnRetry.isVisible = true
        })

        return binding.root
    }

    private fun onItemClicked(movie: Movie) {
        activity.let {
            val intent = Intent(it, MovieDetailActivity::class.java)
            intent.putExtra("movie_id", movie.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }
}