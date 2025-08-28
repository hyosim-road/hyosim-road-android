package com.hyosim.hamkkae.presentation.main.photo_album.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemPhotoPagerBinding
import com.hyosim.hamkkae.domain.model.Album.*
import com.hyosim.hamkkae.presentation.main.photo_album.TextAdapter

// PhotoPagerAdapter.kt
class PhotoPagerAdapter : ListAdapter<Photo, PhotoPagerAdapter.PhotoViewHolder>(PhotoPagerDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            val flexboxLayoutManager = FlexboxLayoutManager(binding.root.context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }

            with(binding){
                ivPhoto.load(photo.url){
                    transformations(RoundedCornersTransformation(16f))
                }
                tvPlace.text = photo.place
                tvTime.text = photo.time
                tvDescription.text = photo.description

                val keywordAdapter = TextAdapter()
                rvKeywords.layoutManager = flexboxLayoutManager
                rvKeywords.adapter = keywordAdapter

                val modifyList = photo.keywords.map {
                    binding.root.context.getString(
                        R.string.photo_album_tag,
                        it
                    )
                }
                keywordAdapter.submitList(modifyList)
            }
        }
    }
}

object PhotoPagerDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}