package com.hyosimroad.hamkkae.presentation.main.photo_album.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityPhotoAlbumDetailBinding
import com.hyosimroad.hamkkae.presentation.main.photo_album.PhotoAlbumViewModel
import com.hyosimroad.hamkkae.presentation.main.photo_album.TextAdapter
import com.hyosimroad.hamkkae.util.GallerySaver
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.getValue

class PhotoAlbumDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoAlbumDetailBinding
    private val photoAlbumViewModel: PhotoAlbumViewModel by viewModels()
    private val photoAlbumDetailViewModel: PhotoAlbumDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityPhotoAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        val id = intent.getIntExtra("albumId", 1)
        photoAlbumDetailViewModel.album = photoAlbumViewModel.albumList[id - 1]

        binding.tvTitle.text = photoAlbumDetailViewModel.album.name
        binding.tvSubtitle.text = getString(
            R.string.main_trip_record_during,
            photoAlbumDetailViewModel.album.startDate,
            photoAlbumDetailViewModel.album.endDate
        )

        setKeywords()
        setPhotos()
        downloadPhotos()
    }

    private fun setKeywords() {
        val keywordAdapter = TextAdapter()
        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }
        binding.rvKeywords.layoutManager = flexboxLayoutManager
        binding.rvKeywords.adapter = keywordAdapter
        val modifyList = photoAlbumDetailViewModel.album.keywords.map {
            getString(
                R.string.photo_album_tag,
                it
            )
        }
        keywordAdapter.submitList(modifyList)
    }

    private fun setPhotos() {
        with(binding) {
            tvPhotoCount.text = getString(
                R.string.photo_album_detail_photo_count,
                photoAlbumDetailViewModel.album.photos.size
            )

            val photoAdapter = PhotoAdapter(onItemClickListener = { photoId ->
                Timber.d("id: $photoId")
                val dialog = PhotoAlbumDetailDialogFragment.newInstance(photoId)
                dialog.show(supportFragmentManager, "PhotoAlbumDetailDialogFragment")
            })
            rvPhotos.adapter = photoAdapter
            photoAdapter.submitList(photoAlbumDetailViewModel.album.photos)
        }
    }

    private fun downloadPhotos(){
        binding.btnDownload.setOnClickListener {
            lifecycleScope.launch {
                val photoList = photoAlbumDetailViewModel.album.photos.map{it.url}
                if(photoList.isEmpty()){
                    Toast.makeText(this@PhotoAlbumDetailActivity, "저장할 사진이 없어요.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val count = GallerySaver.saveUrls(this@PhotoAlbumDetailActivity, photoList, "Pictures/hamkkae")
                Toast.makeText(this@PhotoAlbumDetailActivity, "${count}개의 사진을 저장했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}