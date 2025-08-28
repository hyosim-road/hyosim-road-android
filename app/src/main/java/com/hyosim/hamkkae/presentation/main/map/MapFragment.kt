package com.hyosim.hamkkae.presentation.main.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentMapBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import timber.log.Timber

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = requireNotNull(_binding) { "map fragment is null" }
    private var kakaoMap: KakaoMap? = null
    private var currentLatLng: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("selectTripInfoFragment started!")
        setting()
    }

    private fun setting() {
        checkAndGetCurrentLocation()
    }

    private fun checkAndGetCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("no permission about location")
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        Timber.d("get current location")
        getCurrentLocationAndStartMap()
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun getCurrentLocationAndStartMap() {
        val fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLatLng = LatLng.from(location.latitude, location.longitude)
            } else {
                Timber.w("위치 가져올 수 없음. 기본 위치 사용")
                currentLatLng = LatLng.from(37.5665, 126.9780) // 서울시청 fallback
            }

            startMapWithInitialPosition()
        }.addOnFailureListener {
            Timber.e("위치 실패: $it")
            currentLatLng = LatLng.from(37.5665, 126.9780) // fallback
            startMapWithInitialPosition()
        }
    }

    private fun startMapWithInitialPosition() {
        val mapView = binding.mapView

        mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {}

                override fun onMapError(error: Exception) {
                    Timber.e("지도 오류: $error")
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(map: KakaoMap) {
                    kakaoMap = map
                    Timber.d("KakaoMap 준비 완료!")
                    makeLabel()
                }

                override fun getPosition(): com.kakao.vectormap.LatLng {
                    // 현재 위치 반환 (null이면 fallback 사용)
                    return currentLatLng ?: LatLng.from(37.5665, 126.9780)
                }
            }
        )
    }

    private fun makeLabel() {
        /*val style = kakaoMap!!.labelManager!!.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.ic_location)))
        val option = LabelOptions.from(LatLng.from(currentLatLng!!.latitude, currentLatLng!!.longitude)).setStyles(style)
        val layer = kakaoMap!!.labelManager!!.getLayer()
        val label = layer!!.addLabel(option)*/
        val labelManager = kakaoMap?.labelManager!!
        val style = labelManager.addLabelStyles(
            LabelStyles.from(LabelStyle.from(R.drawable.ic_location))
        )
        val layer = labelManager.getLayer()!!

        val labelData = listOf(
            Triple(1, 37.299171, 127.045373),
            Triple(2, 37.298938, 127.043893),
            Triple(3, 37.299099, 127.042824)
        )

        for((id, lat, lng)in labelData){
            val latLng = LatLng.from(lat, lng)
            val labelOption = LabelOptions.from(latLng).setStyles(style).setTag(id)
            layer.addLabel(labelOption)
        }

        clickLabel()
    }

    private fun clickLabel(){
        kakaoMap?.setOnLabelClickListener {kakaoMap, labelLayer, label->
            val tag = label.tag
            Timber.d("label clicked!: ${tag}")

            (activity as? MapLabelClickListener)?.onLabelClicked(tag as Int, currentLatLng!!)
            true
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Handler(Looper.getMainLooper()).post {
                    if (_binding != null && isAdded) {
                        getCurrentLocationAndStartMap()
                    }
                }

            } else {
                Toast.makeText(requireContext(), "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}