package com.hyosim.hamkkae.presentation.main.upload_photo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivityUploadPhotoCompleteBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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