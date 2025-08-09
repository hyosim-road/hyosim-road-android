package com.hyosimroad.hamkkae.presentation.main.photo_album

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityPhotoAlbumBinding
import com.hyosimroad.hamkkae.presentation.main.MainViewModel
import timber.log.Timber
import kotlin.getValue

class PhotoAlbumActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPhotoAlbumBinding
    private val photoAlbumViewModel: PhotoAlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityPhotoAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
       setStatistics()
    }

    private fun setStatistics(){
        with(binding){
            tvTotalAlbumsCount.text="3"
            tvTotalPhotosCount.text="34"
            tvTotalDaysCount.text="3"

            rvAlbums.visibility = View.VISIBLE
        }

        setAlbums()
    }

    private fun setAlbums(){
        val photoAlbumAdapter = PhotoAlbumAdapter()
        binding.rvAlbums.adapter = photoAlbumAdapter
        photoAlbumAdapter.submitList(photoAlbumViewModel.albumList)
    }
}