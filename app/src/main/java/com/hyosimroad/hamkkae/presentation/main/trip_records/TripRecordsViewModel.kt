package com.hyosimroad.hamkkae.presentation.main.trip_records

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Album
import com.hyosimroad.hamkkae.domain.model.Album.Photo
import com.hyosimroad.hamkkae.domain.model.TripRecord
import com.hyosimroad.hamkkae.util.StateConstants

class TripRecordsViewModel: ViewModel() {
    val tripRecordList = listOf(
        TripRecord(
            id = 1,
            content = "경주 여행",
            startDate = "2024.09.05",
            endDate = "2024.09.07",
            state = StateConstants.TYPE_BEFORE_STARTING,
            place = "경상북도 경주시",
            photos = emptyList(),
            answers = listOf(
                TripRecord.Answer(id = 3, place = "경주 숙소 추천", content = "황리단길 근처 한옥 숙소 추천합니다."),
                TripRecord.Answer(id = 4, place = "불국사 관람 소요 시간", content = "넉넉하게 2시간 정도 잡으면 좋아요.")
            ),
            keywords = listOf("경주", "역사여행", "혼자여행", "문화유산", "가을여행")
        ),
        TripRecord(
            id = 2,
            content = "부산 여행",
            startDate = "2024.12.20",
            endDate = "2024.12.22",
            state = StateConstants.TYPE_COMPLETE,
            place = "부산광역시",
            photos = listOf(
                Photo(
                    id = 6,
                    url = "https://www.saha.go.kr/tour/images/sub/img_01_01_01.jpg",
                    place = "다대포 해수욕장",
                    description = "넓은 백사장과 아름다운 일몰을 자랑하는 해변",
                    time = "2025.07.01 18:30",
                    keywords = listOf("일몰명소", "해수욕", "갯벌체험", "산책")
                ),
                Photo(
                    id = 7,
                    url = "https://visitbusan.net/uploadImgs/files/cntnts/20221215173025725_oen",
                    place = "해운대 해수욕장",
                    description = "부산을 대표하는 유명한 해수욕장",
                    time = "2025.07.02 15:00",
                    keywords = listOf("백사장", "여름휴가", "바다수영", "축제")
                ),
                Photo(
                    id = 8,
                    url = "https://cdn.epnc.co.kr/news/photo/202001/93682_85075_3859.jpg",
                    place = "광안대교",
                    description = "밤바다를 가로지르는 아름다운 다리, 야경 명소",
                    time = "2025.07.02 21:00",
                    keywords = listOf("야경드라이브", "불꽃축제", "바다전망", "랜드마크")
                )
            ),
            answers = listOf(
                TripRecord.Answer(id = 5, place = "부산 맛집 리스트", content = "돼지국밥, 씨앗호떡 꼭 먹어봐야지!")
            ),
            keywords = listOf("부산", "친구랑여행", "겨울여행", "먹방", "바다")
        ),
        TripRecord(
            id = 3,
            content = "제주도 여행",
            startDate = "2024.07.15",
            endDate = "2024.07.18",
            state = StateConstants.TYPE_COMPLETE,
            place = "제주특별자치도",
            photos = listOf(
                Photo(
                    id = 101,
                    url = "https://api.cdn.visitjeju.net/photomng/imgpath/202408/27/f7057419-8ad8-4fac-8b01-ed1119806687.jpg",
                    place = "협재해수욕장",
                    description = "에메랄드빛 바다가 아름다운 협재",
                    time = "2024.07.15 14:30",
                    keywords = listOf("바다", "해변", "가족여행", "풍경")
                ),
                Photo(
                    id = 102,
                    url = "https://example.com/jeju_hallasan.jpg",
                    place = "한라산",
                    description = "구름 위의 산책, 한라산 등반",
                    time = "2024.07.16 10:00",
                    keywords = listOf("등산", "자연", "오름", "정상")
                )
            ),
            answers = listOf(
                TripRecord.Answer(id = 1, place = "협재해수욕장 근처 맛집", content = "해물라면 정말 맛있었어요!"),
                TripRecord.Answer(id = 2, place = "한라산 등반 팁", content = "오전 일찍 출발하는 것이 좋아요.")
            ),
            keywords = listOf("제주도", "가족여행", "여름휴가", "힐링", "자연")
        )
    )
}