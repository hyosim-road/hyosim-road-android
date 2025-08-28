package com.hyosim.hamkkae.presentation.main.photo_album.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hyosim.hamkkae.databinding.ItemImageBinding
import com.hyosim.hamkkae.domain.model.Album

class PhotoAdapter(
    private val onItemClickListener: (Int) -> Unit
): ListAdapter<Album.Photo, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class PhotoViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Album.Photo){
            binding.ivImage.load(photo.url){
                transformations(RoundedCornersTransformation(16f))
            }
            binding.ivImage.setOnClickListener {
                onItemClickListener(photo.id)
            }
        }
    }
}

object PhotoDiffCallback : DiffUtil.ItemCallback<Album.Photo>() {
    override fun areItemsTheSame(oldItem: Album.Photo, newItem: Album.Photo): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Album.Photo, newItem: Album.Photo): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}