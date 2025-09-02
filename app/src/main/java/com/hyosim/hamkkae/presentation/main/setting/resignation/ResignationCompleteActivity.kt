package com.hyosim.hamkkae.presentation.main.setting.resignation

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivityResignationCompleteBinding
import com.hyosim.hamkkae.presentation.auth.AuthActivity

class ResignationCompleteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityResignationCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityResignationCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        binding.btnGoHome.setOnClickListener {
            goToAuth()
        }

        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt()
                binding.tvAfterSeconds.text = getString(R.string.resignation_complete_after_seconds, secondsLeft)
            }

            override fun onFinish() {
                goToAuth()
            }
        }.start()
    }

    private fun goToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}