package com.hyosim.hamkkae.presentation.main.home

import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.domain.model.Album.Photo
import com.hyosim.hamkkae.domain.model.Recent
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.domain.model.TripRecord
import com.hyosim.hamkkae.domain.repository.HomeRepository
import com.hyosim.hamkkae.extension.home.ProgressTripState
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_HISTORY
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_TEMPLE
import com.hyosim.hamkkae.util.RecentConstants.TRIP_COMPLETE
import com.hyosim.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosim.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosim.hamkkae.util.StateConstants.TYPE_IN_PROCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): BaseViewModel() {
    private var _progressTripState =
        MutableStateFlow<ProgressTripState>(ProgressTripState.Loading)
    val progressTripState: StateFlow<ProgressTripState> = _progressTripState.asStateFlow()

    fun progressTrip(){
        viewModelScope.launch {
            homeRepository.progressTrip().onSuccess { response->
                val original = response.data!!

                // 오늘 날짜 (yyyy-MM-dd)
                val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA).format(System.currentTimeMillis())
                val tomorrow = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA).format(System.currentTimeMillis() + 24 * 60 * 60 * 1000)

                // itinerary 날짜만 교체 (Day 1 = 오늘, Day 2 = 내일)
                val updatedItinerary = original.itinerary.mapIndexed { index, day ->
                    val newDay = if (index == 0) today else tomorrow
                    day.copy(
                        day = newDay,
                        attractions = day.attractions.map { attr ->
                            // startTime, endTime도 날짜 교체
                            val startTime = attr.startTime.substring(11) // "HH:mm:ss..."
                            val endTime = attr.endTime.substring(11)
                            attr.copy(
                                startTime = "${newDay}T$startTime",
                                endTime = "${newDay}T$endTime"
                            )
                        }
                    )
                }

                val updatedCourse = original.copy(itinerary = updatedItinerary)

                _progressTripState.value = ProgressTripState.Success(updatedCourse)
                //_progressTripState.value= ProgressTripState.Success(response.data!!)
            }.onFailure {
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        val apiError = parseStatusCode(errorBodyString)

                        _progressTripState.value = ProgressTripState.Error(
                            status = apiError.status,
                            message = apiError.message,
                        )
                    } catch (e: Exception) {
                        Timber.e("Error parsing error body: $e")
                        _progressTripState.value = ProgressTripState.Error(
                            status = null,
                            message = "알 수 없는 에러가 발생했습니다.",
                        )
                    }
                } else {
                    _progressTripState.value = ProgressTripState.Error(
                        status = null,
                        message = it.message,
                    )
                }
            }
        }
    }

    fun progressStateLoading(){
        _progressTripState.value = ProgressTripState.Loading
    }

    val todayScheduleList = listOf(
        TodaySchedule(1, "불국사", KEYWORD_TEMPLE, "09:00", "12:00", TYPE_COMPLETE),
        TodaySchedule(2, "석굴암", KEYWORD_HISTORY, "13:00", "16:00", TYPE_IN_PROCESS),
        TodaySchedule(3, "첨성대", KEYWORD_HISTORY, "17:00", "20:00", TYPE_BEFORE_STARTING),
        TodaySchedule(4, "동궁과 월지", KEYWORD_HISTORY, "13:00", "16:00",TYPE_BEFORE_STARTING)
    )


    val recentActivityList = listOf(
        Recent(1, TRIP_COMPLETE, "경주 여행을 성공적으로 완료했어요!", 0),
  /*      Recent(2, PHOTO_UPLOAD, "동궁과 월지에서 가족사진 업로드", 2),
        Recent(3, ANSWER_COMPLETE, "마지막 질문에 답변 완료", 180),
        Recent(3, TRIP_START, "경주 여행 시작!", 200),
        Recent(3, TRIP_COMPLETE, "부산 여행을 성공적으로 완료했어요!", 300),*/
    )

    val tripRecordList = listOf(
        TripRecord(
            id = 1,
            content = "나 홀로 떠난 경주 역사 탐방, 신라의 숨결을 느끼다.",
            startDate = "2024.09.05",
            endDate = "2024.09.07",
            state = TYPE_BEFORE_STARTING,
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
            content = "친구들과 함께하는 부산 먹방 투어 예정! 벌써부터 기대된다.",
            startDate = "2024.12.20",
            endDate = "2024.12.22",
            state = TYPE_COMPLETE,
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
            content = "가족들과 함께한 즐거운 제주도 여행, 맛있는 것도 많이 먹고 예쁜 풍경도 실컷 봤다!",
            startDate = "2024.07.15",
            endDate = "2024.07.18",
            state = TYPE_COMPLETE,
            place = "제주특별자치도",
            photos = listOf(
                Photo(
                    id = 101,
                    url = "https://example.com/jeju_beach.jpg",
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