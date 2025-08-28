package com.hyosim.hamkkae.presentation.main.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivitySettingBinding
import com.hyosim.hamkkae.presentation.main.setting.user.ChangePwNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity(), ChangePwNavigator {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    private fun initBinds() {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPasswordChanged() {
        // NavController 기반 이동
        findNavController(R.id.fcv_find).navigate(
            R.id.settingFragment,
            null,
            navOptions {
                popUpTo(findNavController(R.id.fcv_find).graph.startDestinationId) {
                    inclusive = true
                }
            }
        )
    }
}