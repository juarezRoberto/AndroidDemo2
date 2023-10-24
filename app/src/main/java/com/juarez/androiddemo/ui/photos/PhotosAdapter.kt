package com.juarez.androiddemo.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juarez.androiddemo.databinding.ItemPhotoBinding
import com.juarez.androiddemo.domain.models.Photo
import com.juarez.androiddemo.utils.loadFirebaseImage

class PhotosAdapter(private val onDeleteItem: (position: Int) -> Unit) :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        with(holder) {
            binding.apply {
                imgFirePhoto.loadFirebaseImage(photo.url)
                txtFireName.text = photo.filename
                txtFireSize.text = "${photo.size} bytes."
                btnDeletePhoto.setOnClickListener { onDeleteItem(position) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem == newItem
    }
}