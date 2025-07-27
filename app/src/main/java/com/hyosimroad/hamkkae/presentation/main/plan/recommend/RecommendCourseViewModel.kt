package com.hyosimroad.hamkkae.presentation.main.plan.recommend

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Course

class RecommendCourseViewModel : ViewModel() {
    val courseList = listOf(
        Course(
            1, "경주 역사문화 코스", listOf("사찰", "역사", "문화"),
            listOf(
                Course.Place(1, "불국사", "09:00 ~ 11:00"),
                Course.Place(2, "석굴암", "11:00 ~ 13:00"),
                Course.Place(3, "첨성대", "13:00 ~ 15:00"),
                Course.Place(4, "동궁과 월지", "15:00 ~ 17:00"),
            ), "2박 3일"
        ),
        Course(
            2, "부산 바다와 미식 코스", // 코스 이름
            listOf("해변", "맛집", "야경", "시장"), // 태그
            listOf( // 장소 목록
                Course.Place(1, "해운대 해수욕장", "10:00 ~ 12:00"),
                Course.Place(2, "자갈치 시장 (점심식사)", "12:30 ~ 14:00"),
                Course.Place(3, "감천문화마을", "14:30 ~ 16:30"),
                Course.Place(4, "더베이 101 (야경)", "19:00 ~ 21:00")
            ),
            "1박 2일" // 여행 기간
        )
    )
}