package com.hyosim.hamkkae.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyosim.hamkkae.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    private fun initBinds() {
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}