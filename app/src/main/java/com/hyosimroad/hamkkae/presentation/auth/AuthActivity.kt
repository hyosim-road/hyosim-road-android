package com.hyosimroad.hamkkae.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyosimroad.hamkkae.databinding.ActivityAuthBinding
import com.hyosimroad.hamkkae.databinding.ActivityPlanBinding
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