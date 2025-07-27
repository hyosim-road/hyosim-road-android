package com.hyosimroad.hamkkae.presentation.main.plan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityPlanBinding

class PlanActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        binding.tvTripProgressTitle.text=getString(R.string.recommend_course_progress_title)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}