package com.hyosimroad.hamkkae.presentation.main.map

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Location

class MapViewModel: ViewModel() {
    val locationList = listOf(
        Location(
            id = 1,
            name = "불국사",
            distance = "100m", // 현재 위치로부터의 거리 예시
            latitude = 35.789675,
            longitude = 129.332285,
            type = "사찰",
            time = "09:00 ~ 18:00", // 실제 운영시간은 계절별로 다를 수 있습니다.
            amenityList = listOf("주차장", "화장실", "매점", "문화관광해설", "휠체어 대여"),
            image = "https://i.namu.wiki/i/XO6E06_qrvaiZnbaLsHC-Ov1z4WI8vRqSn_R9IqHobEgHD2gYLLN3Ldi3zvnFLRYcLTQmWpRuAdsfH_2IrNNPQ.webp"
        ),
        Location(
            id = 2,
            name = "첨성대",
            distance = "500m",
            latitude=35.834635,
            longitude = 129.219109,
            type = "역사유적",
            time = "09:00 ~ 22:00 (하절기)", // 야간 개장 여부 및 시간 확인 필요
            amenityList = listOf("주차장", "화장실", "기념품샵", "자전거 대여소 인근", "공원"),
            image = "https://www.gyeongju.go.kr/upload/content/thumb/gyimage/%EC%B2%A8%EC%84%B1%EB%8C%80%EC%9D%98%20%EC%95%84%EB%A6%84%EB%8B%A4%EC%9B%80.jpg" // 예시 이미지 URL
        ),
        Location(
            id = 3,
            name = "동궁과 월지 (안압지)",
            distance = "1.2km",
            latitude = 35.835001,
            longitude = 129.226253,
            type = "궁궐터/호수",
            time = "09:00 ~ 22:00 (입장마감 21:30)",
            amenityList = listOf("주차장", "화장실", "매표소", "유모차/휠체어 접근로", "야경 명소"),
            image = "https://www.gyeongju.go.kr/upload/content/thumb/20200317/5F92275758614941B3EB69A32A12CA4E.jpg" // 예시 이미지 URL
        )
    )
}