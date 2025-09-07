package com.hyosim.hamkkae.presentation.main.map

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.databinding.ActivityMapBinding
import com.hyosim.hamkkae.domain.model.Location
import com.kakao.vectormap.LatLng

class MapActivity: AppCompatActivity(), MapLabelClickListener {
    private lateinit var binding: ActivityMapBinding
    private lateinit var course: ProgressTripResponseDto
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        val bottomSheet = binding.bottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)

        behavior.isHideable = true           // 숨김 허용
        behavior.state = BottomSheetBehavior.STATE_HIDDEN // 처음에 숨김 상태

        course = intent.getParcelableExtra("course")
            ?: throw IllegalArgumentException("Course is missing")

        mapViewModel.setCourse(course)
    }

    override fun onLabelClicked(location: Location, currentLatLng: LatLng) {
        val bottomSheet = binding.bottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val location = mapViewModel.locationList.value!!.find { it.id == location.id }!!
        with(binding) {
            tvName.text = location.name
            tvDistance.text = location.distance
            tvType.text = location.type
            tvTime.text = location.time
            ivImage.load(location.image) {
                transformations(RoundedCornersTransformation(10f))
            }

            val flexboxLayoutManager = FlexboxLayoutManager(this@MapActivity).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.FLEX_START
            }

            val amenityAdapter = MapAmenityAdapter()

            rvAmenities.layoutManager = flexboxLayoutManager
            rvAmenities.adapter = amenityAdapter
            amenityAdapter.submitList(location.amenityList)

            btnNavigate.setOnClickListener {
                try{
                    val uri = Uri.parse("kakaomap://route?sp=${currentLatLng.latitude},${currentLatLng.longitude}&ep=${location.latitude},${location.longitude}&by=car")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }catch (e: ActivityNotFoundException) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.daum.android.map"))
                    startActivity(intent)
                }
            }
        }
    }
}