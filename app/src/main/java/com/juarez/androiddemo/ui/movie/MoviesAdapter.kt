package com.juarez.androiddemo.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juarez.androiddemo.databinding.ItemMovieBinding
import com.juarez.androiddemo.domain.models.Movie
import com.juarez.androiddemo.utils.loadPosterImage

class MoviesAdapter(private val onItemClicked: (movie: Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(DiffCallback()) {

    class MoviesViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams = ViewGroup.LayoutParams(
            (parent.measuredWidth * 0.5).toInt(),
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder) {
            binding.apply {
                txtTitle.text = movie.title
                imgPhoto.loadPosterImage(movie.poster_path)
            }
            itemView.setOnClickListener { onItemClicked(movie) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}