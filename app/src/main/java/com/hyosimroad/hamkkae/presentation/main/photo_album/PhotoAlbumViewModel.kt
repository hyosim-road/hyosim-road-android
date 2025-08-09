package com.hyosimroad.hamkkae.presentation.main.photo_album

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Album

class PhotoAlbumViewModel: ViewModel() {
    val albumList = listOf(
        Album(1, "경주 역사문화 코스", "2025.06.14", "2025.06.17", "경주시", 3, listOf("감동", "경건함", "가족애", "전통미")),
        Album(2, "부산 해변 드라이브", "2025.07.01", "2025.07.03", "부산시", 2, listOf("힐링", "바다", "낭만", "드라이브")),
        Album(3, "제주도 한라산 등반", "2025.08.10", "2025.08.12", "제주시", 1, listOf("도전", "자연", "성취감", "풍경"))
    )
}