package com.hyosim.hamkkae.domain.model

data class Info (
    val id: String,                // 고유 식별자 (이름이나 서버 id)
    val type: String,              // "식당" / "숙소"
    val name: String,              // 이름
    val cost: String? = null,      // 가격 정보 (예: "인당 2만원", "1박 16만원")
    val location: String? = null,  // 주소
    val description: String? = null, // 설명 (메뉴, 숙소 설명 등)
    val additionList: List<String> = emptyList(), // 추가 정보 (태그 등)
    val optionList: List<String> = emptyList(),   // 옵션 정보
    val checkin: String? = null,   // 숙소 전용: 체크인 시간
    val checkout: String? = null   // 숙소 전용: 체크아웃 시간
)