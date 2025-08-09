package com.hyosimroad.hamkkae.presentation.main.photo_album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemPhotoAlbumBinding
import com.hyosimroad.hamkkae.domain.model.Album
import timber.log.Timber

class PhotoAlbumAdapter: ListAdapter<Album, PhotoAlbumAdapter.PhotoAlbumViewHolder>(PhotoAlbumDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoAlbumViewHolder {
        val binding = ItemPhotoAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoAlbumViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PhotoAlbumViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class PhotoAlbumViewHolder(private val binding: ItemPhotoAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            with(binding) {
                tvTripName.text = album.name
                tvDuring.text = binding.root.context.getString(
                    R.string.main_trip_record_date,
                    album.startDate,
                    album.endDate
                )
                tvInfo.text = binding.root.context.getString(
                    R.string.photo_album_info_format,
                    album.place,
                    album.photos
                )

                val textAdapter = TextAdapter()
                val layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                rvKeywords.layoutManager = layoutManager
                rvKeywords.adapter=textAdapter

                val modifyList = album.keywords.map {
                    binding.root.context.getString(
                        R.string.photo_album_tag,
                        it
                    )
                }
                Timber.d("modifylist: $modifyList")
                textAdapter.submitList(modifyList)
            }
        }
    }
}

object PhotoAlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}