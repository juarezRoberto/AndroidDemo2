package com.juarez.upaxdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juarez.upaxdemo.R
import com.juarez.upaxdemo.models.Movie
import com.juarez.upaxdemo.utils.Constants
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val movies: ArrayList<Movie>,
    private val onItemClicked: (Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    fun updateData(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle by lazy { view.findViewById(R.id.txtTitle) as TextView }
        val imgPoster by lazy { view.findViewById(R.id.imgPhoto) as ImageView }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        view.layoutParams = ViewGroup.LayoutParams(
            (parent.measuredWidth * 0.5).toInt(),
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ViewHolder(view)
    }

//    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                txtTitle.text = title
                Picasso.get()
                    .load("${Constants.IMG_BASE_URL}$poster_path")
                    .into(imgPoster)
            }
            itemView.setOnClickListener { onItemClicked(movies[position]) }
        }
    }

    override fun getItemCount() = movies.size
}