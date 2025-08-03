package com.hyosimroad.hamkkae.presentation.main.upload_photo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityUploadPhotoCompleteBinding
import timber.log.Timber
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.getValue

class UploadPhotoCompleteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUploadPhotoCompleteBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityUploadPhotoCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
       with(binding){
           tvCompleteDetail.text = getString(R.string.upload_photo_place, "불국사")

           val formatter = DateTimeFormatter.ofPattern("HH:mm")
           tvTime.text = LocalTime.now().format(formatter)

           val image = intent.getStringExtra("image")
           ivPhoto.load(image)
       }

        clickHome()
    }

    private fun clickHome(){
        binding.btnHome.setOnClickListener {
            finish()
        }
    }
}