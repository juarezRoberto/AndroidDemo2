package com.juarez.upaxdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.juarez.upaxdemo.databinding.ActivityMovieDetailBinding
import com.juarez.upaxdemo.repositories.MovieRepository
import com.juarez.upaxdemo.utils.Constants
import com.juarez.upaxdemo.viewmodels.MoviesViewModel
import com.juarez.upaxdemo.viewmodels.MoviesViewModelFactory
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId = intent.getIntExtra("movie_id", 1)
        val provider = MoviesViewModelFactory(MovieRepository())
        viewModel = ViewModelProvider(this, provider).get(MoviesViewModel::class.java)

        viewModel.getMovieDetail(movieId)
        viewModel.movie.observe(this, {
            Picasso.get()
                .load("${Constants.IMG_BASE_URL}${it.poster_path}")
                .into(binding.imgPoster)
            binding.txtName.text = it.title
        })
        viewModel.loading.observe(this, {
            binding.progressMovieDetail.isVisible = it
        })
    }
}