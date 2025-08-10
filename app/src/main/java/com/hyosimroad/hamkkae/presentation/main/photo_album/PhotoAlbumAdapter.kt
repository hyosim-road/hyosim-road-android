package com.hyosimroad.hamkkae.presentation.main.photo_album

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemPhotoAlbumBinding
import com.hyosimroad.hamkkae.domain.model.Album
import timber.log.Timber

class PhotoAlbumAdapter(
    private val clickAlbum:(Int) -> Unit
): ListAdapter<Album, PhotoAlbumAdapter.PhotoAlbumViewHolder>(PhotoAlbumDiffCallback) {
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
        holder.bind(getItem(position), position)
    }

    inner class PhotoAlbumViewHolder(private val binding: ItemPhotoAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, position: Int) {
            with(binding) {
                if (position == 0) {
                    // 첫 번째 아이템일 경우
                    cvAlbum.strokeColor = root.context.getColor(R.color.primary_orange)
                    cvAlbum.cardElevation = 2f.dpToPx() // 2dp
                    tvInProcess.visibility= View.VISIBLE
                } else {
                    // 그 외 아이템일 경우 (원래 XML에 설정된 값으로 되돌리기)
                    cvAlbum.strokeColor = root.context.getColor(R.color.state_complete_gray)
                    cvAlbum.cardElevation = 0f // 0dp
                    tvInProcess.visibility= View.GONE
                }

                tvTripName.text = album.name
                tvDuring.text = binding.root.context.getString(
                    R.string.main_trip_record_during,
                    album.startDate,
                    album.endDate
                )
                tvInfo.text = binding.root.context.getString(
                    R.string.photo_album_info_format,
                    album.place,
                    album.photos.size
                )

                ivImage.load(album.photos[0].url)

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

                clAlbum.setOnClickListener {
                    clickAlbum(album.id)
                }
            }
        }

        // dp를 px로 변환하는 확장 함수를 클래스 밖에 추가하면 편리합니다.
        fun Float.dpToPx(): Float {
            return this * Resources.getSystem().displayMetrics.density
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