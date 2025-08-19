package com.hyosimroad.hamkkae.presentation.main.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyosimroad.hamkkae.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    private fun initBinds() {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}