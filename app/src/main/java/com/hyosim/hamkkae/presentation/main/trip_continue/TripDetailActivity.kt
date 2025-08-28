package com.hyosim.hamkkae.presentation.main.trip_continue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivityTripDetailBinding
import com.hyosim.hamkkae.presentation.main.map.MapActivity
import com.hyosim.hamkkae.presentation.main.trip_continue.album.TripDetailAlbumFragment
import com.hyosim.hamkkae.presentation.main.trip_continue.schedule.TripDetailScheduleFragment

class TripDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTripDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityTripDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        currentInfo()
        setTab()
        clickMap()
    }

    private fun currentInfo(){
        with(binding){
            tvCurrent.text = getString(R.string.main_trip_current_location, "불국사")
            tvTime.text = getString(R.string.main_time, "09:00", "10:00")
        }
    }

    private fun setTab(){
        binding.tlDetail.apply{
            addTab(newTab().setText("전체 일정").setIcon(R.drawable.ic_calendar_red_24), true)
            addTab(newTab().setText("여행 사진첩").setIcon(R.drawable.ic_camera_white_24))
        }

        binding.vpDetail.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int) =
                if (position == 0) TripDetailScheduleFragment() else TripDetailAlbumFragment()
        }
        binding.vpDetail.isUserInputEnabled = false // 스와이프 막고 탭으로만 전환

        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, pos ->
            if (pos == 0) {
                tab.setText("전체 일정").setIcon(R.drawable.ic_calendar_red_24)
            } else {
                tab.setText("여행 사진첩").setIcon(R.drawable.ic_camera_white_24)
            }
        }.attach()
    }

    private fun clickMap(){
        binding.btnMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}