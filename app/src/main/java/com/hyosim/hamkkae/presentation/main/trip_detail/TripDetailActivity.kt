package com.hyosim.hamkkae.presentation.main.trip_detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.home.findAttraction
import com.hyosim.hamkkae.databinding.ActivityTripDetailBinding
import com.hyosim.hamkkae.extension.home.AttractionResult
import com.hyosim.hamkkae.presentation.main.home.noWordBreak
import com.hyosim.hamkkae.presentation.main.map.MapActivity
import com.hyosim.hamkkae.presentation.main.trip_detail.album.TripDetailAlbumFragment
import com.hyosim.hamkkae.presentation.main.trip_detail.schedule.TripDetailScheduleFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TripDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTripDetailBinding
    private lateinit var course: ProgressTripResponseDto
    private val tripDetailViewModel: TripDetailViewModel by viewModels()

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
        course = intent.getParcelableExtra("course")
            ?: throw IllegalArgumentException("Course is missing")

        // ViewModel 에 저장
        tripDetailViewModel.setCourse(course)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_course_detail, TripDetailScheduleFragment())
            .commit()

        setTripDayInfo()
        currentInfo()
        //setTab()
        clickMap()
    }

    private fun setTripDayInfo() {
        // 일정 없을 때 예외 방지
        val departureDayStr = course.itinerary.firstOrNull()?.day?.substring(0, 10) ?: return
        val arrivalDayStr = course.itinerary.lastOrNull()?.day?.substring(0, 10) ?: return

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val todayDate = LocalDate.now()
        val departureDate = LocalDate.parse(departureDayStr, formatter)
        val arrivalDate = LocalDate.parse(arrivalDayStr, formatter)

        with(binding) {
            when {
                todayDate.isBefore(departureDate) -> {
                    // 출발 전
                    val diffDays = ChronoUnit.DAYS.between(todayDate, departureDate).toInt()
                    tvDate.text = departureDayStr
                    tvDay.text = "D-$diffDays"
                }
                !todayDate.isAfter(arrivalDate) -> {
                    // 여행 중
                    val diffDays = ChronoUnit.DAYS.between(departureDate, todayDate).toInt() + 1
                    tvDate.text = todayDate.format(formatter)
                    tvDay.text = getString(R.string.main_trip_current_day, diffDays)
                }
                else -> {
                    // 여행 끝
                    tvDate.text = arrivalDayStr
                    tvDay.text = getString(R.string.main_trip_current_day, course.itinerary.size)
                }
            }
        }
    }

    private fun currentInfo() {
        when (val result = course.findAttraction()) {
            is AttractionResult.Current -> {
                binding.tvInProcess.apply {
                    text = getString(R.string.state_in_progress)
                    setTextColor(  ContextCompat.getColor(context, R.color.white))
                    setBackgroundResource(R.drawable.bg_current_orange)
                    visibility = View.VISIBLE
                }
                binding.tvCurrent.text =
                    getString(R.string.main_trip_current_location, result.attraction.name).noWordBreak()
                binding.tvTime.text = getString(
                    R.string.main_time,
                    result.startTime,
                    result.endTime
                )
            }

            is AttractionResult.Next -> {
                binding.tvInProcess.apply {
                    text = getString(R.string.state_before_starting)
                    setTextColor(  ContextCompat.getColor(context, R.color.text_secondary_gray))
                    setBackgroundResource(R.drawable.bg_white_stroke)
                    visibility = View.VISIBLE
                }
                binding.tvCurrent.text =
                    getString(R.string.main_trip_next_location, result.attraction.name).noWordBreak()
                binding.tvTime.text = getString(
                    R.string.main_time,
                    result.startTime,
                    result.endTime
                )
            }

            AttractionResult.None -> {
                binding.tvInProcess.visibility = View.GONE
                binding.tvCurrent.text = getString(R.string.main_trip_all_done)
                binding.tvTime.text = ""
            }
        }
    }

    /*private fun setTab(){
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
    }*/

    private fun clickMap() {
        binding.btnMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }
    }
}