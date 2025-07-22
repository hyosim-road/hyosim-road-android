package com.hyosimroad.hamkkae

import android.app.Activity
import android.os.Bundle
import com.hyosimroad.hamkkae.databinding.ActivityMapBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import timber.log.Timber

class MapActivity: Activity() {
    private lateinit var binding: ActivityMapBinding

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
        val mapView = binding.mapView
        mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    // 지도 API가 정상 종료될 때
                }
                override fun onMapError(error:Exception){
                    //인증 실패 또는 지도 사용 중 에러
                    Timber.e("error: $error")
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    // 지도 API가 정상적으로 준비될 때
                }
            }
        )
    }
}