package com.hyosim.hamkkae.presentation.main.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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