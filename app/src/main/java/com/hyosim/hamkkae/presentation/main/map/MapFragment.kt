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
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.LocationServices
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentMapBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
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
    private val mapViewModel: MapViewModel by activityViewModels()

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
                mapViewModel.updateLocationList(location.latitude, location.longitude)
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

                    val attractions = mapViewModel.locationList.value
                        ?.filter { it.type == "여행지" }
                        ?.map { LatLng.from(it.latitude, it.longitude) }
                        ?: emptyList()

                    val centerLatLng = getCenterLatLng(attractions, currentLatLng ?: LatLng.from(37.5665, 126.9780))

                    // 카메라 이동 + 줌 레벨 10 적용
                    val cameraUpdate = CameraUpdateFactory.newCenterPosition(centerLatLng, 11)
                    kakaoMap?.moveCamera(cameraUpdate)

                    makeLabel()
                }

                override fun getPosition(): com.kakao.vectormap.LatLng {
                    val attractions = mapViewModel.locationList.value
                        ?.filter { it.type == "여행지" }
                        ?.map { LatLng.from(it.latitude, it.longitude) }
                        ?: emptyList()

                    return getCenterLatLng(attractions, currentLatLng ?: LatLng.from(37.5665, 126.9780))
                }
            }
        )
    }

    fun getCenterLatLng(points: List<LatLng>, fallback: LatLng): LatLng {
        return if (points.isNotEmpty()) {
            val avgLat = points.map { it.latitude }.average()
            val avgLng = points.map { it.longitude }.average()
            LatLng.from(avgLat, avgLng)
        } else {
            fallback
        }
    }


    private fun makeLabel() {
        val labelManager = kakaoMap?.labelManager ?: return
        val layer = labelManager.getLayer() ?: return

        // 🟠 Attraction (숫자 마커) — 1~6까지 drawable 준비
        fun getAttractionStyle(order: Int): Int {
            return when (order) {
                1 -> R.drawable.ic_attraction_1
                2 -> R.drawable.ic_attraction_2
                3 -> R.drawable.ic_attraction_3
                4 -> R.drawable.ic_attraction_4
                5 -> R.drawable.ic_attraction_5
                6 -> R.drawable.ic_attraction_6
                else -> R.drawable.ic_attraction_1
            }
        }

        // 🔵 Lodging (숙소)
        val lodgingStyle = labelManager.addLabelStyles(
            LabelStyles.from(LabelStyle.from(R.drawable.ic_lodging))
        )

        // 🟢 Restaurant (식당)
        val restaurantStyle = labelManager.addLabelStyles(
            LabelStyles.from(LabelStyle.from(R.drawable.ic_restaurant))
        )

        val locations = mapViewModel.locationList.value ?: emptyList()

        for (location in locations) {
            val latLng = LatLng.from(location.latitude, location.longitude)

            val style = when (location.type) {
                "여행지" -> {
                    val iconRes = getAttractionStyle(location.id) // id 또는 order 사용
                    labelManager.addLabelStyles(LabelStyles.from(LabelStyle.from(iconRes)))
                }
                "숙소" -> lodgingStyle
                "식당" -> restaurantStyle
                else -> labelManager.addLabelStyles(
                    LabelStyles.from(LabelStyle.from(R.drawable.ic_location)) // fallback
                )
            }

            val option = LabelOptions.from(latLng)
                .setStyles(style)
                .setTag(location.id)

            layer.addLabel(option)
        }

        // 라벨 클릭 리스너 등록
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