package com.hyosim.hamkkae.presentation.main.plan.recommend

import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.domain.repository.PlanRepository
import com.hyosim.hamkkae.extension.plan.CourseRecommendState
import com.hyosim.hamkkae.extension.plan.RegisterState
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
class RecommendCourseViewModel @Inject constructor(
    private val planRepository: PlanRepository,
): BaseViewModel() {
    private var _registerState =
        MutableStateFlow<RegisterState>(RegisterState.Loading)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun register(planData:CourseRecommendRequestDto, course: AiCourseRecommendResponseDto){
        val request = toRequestDto(planData, course)

        viewModelScope.launch {
            planRepository.register(request).onSuccess { response ->
                _registerState.value = RegisterState.Success(course)
            }.onFailure {
                _registerState.value = RegisterState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _registerState.emit(RegisterState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _registerState.emit(RegisterState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }

    private fun toRequestDto(planData:CourseRecommendRequestDto, course: AiCourseRecommendResponseDto): CourseRecommendResponseData {
        return CourseRecommendResponseData(
            departureDate = planData.departureDate,
            arrivalDate = planData.arrivalDate,
            people = planData.numberOfPeople,
            budgetRange = planData.budgetRange,
            travelStyle = planData.travelStyle,
            title = "추천 코스",    // responsedto에서 가져와야함
            plan = CourseRecommendResponseData.Plan(
                itinerary = course.itinerary.map { day ->
                    CourseRecommendResponseData.Plan.ItineraryDay(
                        day = day.day,
                        attractions = day.attractions.map { attraction ->
                            CourseRecommendResponseData.Plan.ItineraryDay.Attraction(
                                name = attraction.name,
                                latitude = attraction.latitude,
                                longitude = attraction.longitude,
                                startTime = attraction.startTime,
                                endTime = attraction.endTime,
                                order = attraction.order,
                                description = attraction.description,
                                address = attraction.address,
                                phone = attraction.phone,
                                priceKrw = attraction.priceKrw
                            )
                        }
                    )
                },
                restaurants = course.restaurants.map { res ->
                    CourseRecommendResponseData.Plan.Restaurant(
                        name = res.name,
                        estimatedCostPerPersonKrw = res.estimatedCostPerPersonKrw,
                        address = res.address,
                        latitude = res.latitude,
                        longitude = res.longitude,
                        description = res.description,
                        signatureMenu = res.signatureMenu,
                        phone = res.phone
                    )
                },
                lodgings = course.lodgings.map { lod ->
                    CourseRecommendResponseData.Plan.Lodging(
                        name = lod.name,
                        pricePerNightKrw = lod.pricePerNightKrw,
                        address = lod.address,
                        latitude = lod.latitude,
                        longitude = lod.longitude,
                        description = lod.description,
                        checkIn = lod.checkIn,
                        checkOut = lod.checkOut,
                        amenities = lod.amenities,
                        phone = lod.phone
                    )
                }
            )
        )
    }

    val mockCourses: List<AiCourseRecommendResponseDto> = listOf(
        // --------------------- 첫 번째 코스 ---------------------
        AiCourseRecommendResponseDto(
            itinerary = listOf(
                AiCourseRecommendResponseDto.Itinerary(
                    day = "2025-09-06",
                    attractions = listOf(
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 강릉시 성산면 삼포암길 133",
                            description = null,
                            endTime = "2025-09-06T12:00:00",
                            latitude = 37.7149097631,
                            longitude = 128.7905413133,
                            name = "국립대관령자연휴양림",
                            order = 1,
                            phone = "",
                            priceKrw = 0,
                            startTime = "2025-09-06T10:00:00"
                        ),
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 평창군 대관령면 대관령마루길 483-32",
                            description = null,
                            endTime = "2025-09-06T18:30:00",
                            latitude = 37.6876427505,
                            longitude = 128.7533288363,
                            name = "대관령양떼목장",
                            order = 2,
                            phone = "",
                            priceKrw = 0,
                            startTime = "2025-09-06T16:10:00"
                        )
                    )
                ),
                AiCourseRecommendResponseDto.Itinerary(
                    day = "2025-09-07",
                    attractions = listOf(
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 강릉시 성산면 어흘리",
                            description = null,
                            endTime = "2025-09-07T12:00:00",
                            latitude = 37.7099545878,
                            longitude = 128.7806256921,
                            name = "제왕산/능경봉",
                            order = 1,
                            phone = "",
                            priceKrw = 0,
                            startTime = "2025-09-07T10:00:00"
                        ),
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 평창군 대관령로 94 대관령 원예농협",
                            description = null,
                            endTime = "2025-09-07T14:30:00",
                            latitude = 37.6726296948,
                            longitude = 128.7066362804,
                            name = "바우파머스몰(대관령마켓)",
                            order = 2,
                            phone = "010-7742-7808",
                            priceKrw = 0,
                            startTime = "2025-09-07T13:30:00"
                        )
                    )
                )
            ),
            lodgings = listOf(
                AiCourseRecommendResponseDto.Lodging(
                    checkOut = "11:00:00",
                    address = "강원특별자치도 평창군 대관령면 송천길 30",
                    amenities = emptyList(),
                    checkIn = "15:00:00",
                    description = null,
                    latitude = 37.6709549819,
                    longitude = 128.7114664518,
                    name = "AM호텔",
                    phone = "프론트 1855-1866 예약실 02-2282-7373",
                    pricePerNightKrw = 160000
                ),
                AiCourseRecommendResponseDto.Lodging(
                    checkOut = "11:00:00",
                    address = "강원특별자치도 평창군 대관령면 송천길 30",
                    amenities = emptyList(),
                    checkIn = "15:00:00",
                    description = null,
                    latitude = 37.6710021996,
                    longitude = 128.7115164459,
                    name = "에이엠호텔",
                    phone = "",
                    pricePerNightKrw = 160000
                )
            ),
            restaurants = listOf(
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 평창군 대관령면 대관령마루길 369",
                    null,
                    20000,
                    37.6849678755,
                    128.7438214416,
                    "가시머리식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 평창군 대관령면 대관령마루길 347",
                    null,
                    20000,
                    37.685315007,
                    128.7412077776,
                    "남경식당",
                    "033-335-5891",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 강릉시 범일로579번길 59",
                    null,
                    20000,
                    37.735625703,
                    128.8764607133,
                    "청송식당",
                    "010-9057-9252",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 평창군 대관령면 송천3길 10",
                    null,
                    20000,
                    37.6714188107,
                    128.7111331341,
                    "넘버나인정육식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 평창군 대관령면 횡계2길 11-1",
                    null,
                    20000,
                    37.673293553,
                    128.7090166011,
                    "남원식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 평창군 대관령면 횡계길 24",
                    null,
                    20000,
                    37.6732963253,
                    128.7087471805,
                    "수미카페",
                    "",
                    null
                )
            )
        ),

        // --------------------- 두 번째 코스 ---------------------
        AiCourseRecommendResponseDto(
            itinerary = listOf(
                AiCourseRecommendResponseDto.Itinerary(
                    day = "2025-09-10",
                    attractions = listOf(
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            "강원특별자치도 양양군 강현면 둔전리",
                            null,
                            "2025-09-10T12:00:00",
                            38.1201275561,
                            128.5506449313,
                            "둔전계곡",
                            1,
                            "",
                            0,
                            "2025-09-10T10:00:00"
                        ),
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            "강원특별자치도 속초시 청대로 207",
                            null,
                            "2025-09-10T14:30:00",
                            38.1839594168,
                            128.5711855239,
                            "청대산",
                            2,
                            "",
                            0,
                            "2025-09-10T13:30:00"
                        )
                    )
                ),
                AiCourseRecommendResponseDto.Itinerary(
                    day = "2025-09-11",
                    attractions = listOf(
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            "강원특별자치도 속초시 관광로 277",
                            null,
                            "2025-09-11T12:00:00",
                            38.1860974964,
                            128.5423075246,
                            "척산족욕공원",
                            1,
                            "",
                            0,
                            "2025-09-11T10:00:00"
                        ),
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            "강원특별자치도 고성군 토성면 원암리 산1",
                            null,
                            "2025-09-11T14:30:00",
                            38.2074685296,
                            128.4660221268,
                            "미시령옛길 울산바위 은하수",
                            2,
                            "",
                            0,
                            "2025-09-11T13:30:00"
                        )
                    )
                )
            ),
            lodgings = listOf(
                AiCourseRecommendResponseDto.Lodging(
                    "11:00:00",
                    "강원특별자치도 속초시 설악산로 998",
                    emptyList(),
                    "15:00:00",
                    null,
                    38.1726899087,
                    128.5002728725,
                    "켄싱턴호텔 설악",
                    "033-635-4001",
                    160000
                ),
                AiCourseRecommendResponseDto.Lodging(
                    "11:00:00",
                    "강원특별자치도 속초시 설악산로836번길 6",
                    emptyList(),
                    "15:00:00",
                    null,
                    38.1684962792,
                    128.5171325821,
                    "뉴스타트설악리조트[한국관광 품질인증/Korea Quality]",
                    "033-637-2239",
                    160000
                )
            ),
            restaurants = listOf(
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 속초시 도문동",
                    null,
                    20000,
                    38.1617597102,
                    128.5962950476,
                    "카페 설악산로",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 양양군 서면 설악로 1417-7",
                    null,
                    20000,
                    38.076131755,
                    128.5032672614,
                    "물레방아식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 양양군 낙산사로 37-2",
                    null,
                    20000,
                    38.120976219,
                    128.6299238227,
                    "전라도식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 속초시 먹거리4길 21",
                    null,
                    20000,
                    38.1957108631,
                    128.5730322116,
                    "완도회식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 속초시 엑스포로 10-3",
                    null,
                    20000,
                    38.1915116955,
                    128.5907196256,
                    "프리미엄 이가한우 정육참숯식당",
                    "",
                    null
                ),
                AiCourseRecommendResponseDto.Restaurant(
                    "강원특별자치도 속초시 중앙로108번길 22-1",
                    null,
                    20000,
                    38.201027209,
                    128.5883667641,
                    "후포식당",
                    "",
                    null
                )
            )
        ),

        /*// --------------------- 세 번째 코스 ---------------------
        CourseResponse(
            itinerary = listOf(
                Itinerary(
                    day = "2025-09-10",
                    attractions = listOf(
                        Attraction("강원특별자치도 원주시 호저면 산현리", null, "2025-09-10T12:00:00", 37.4413186738, 127.8859505952, "칠봉유원지", 1, "", 0, "2025-09-10T10:00:00"),
                        Attraction("강원특별자치도 원주시 호저면 산현리", null, "2025-09-10T14:30:00", 37.4261234293, 127.8978083011, "칠봉체육공원", 2, "", 0, "2025-09-10T13:30:00")
                    )
                ),
                Itinerary(
                    day = "2025-09-11",
                    attractions = listOf(
                        Attraction("강원특별자치도 원주시 호저면 산현리", null, "2025-09-11T12:00:00", 37.4254512962, 127.8984193766, "원주섬강자작나무숲둘레길", 1, "", 0, "2025-09-11T10:00:00"),
                        Attraction("강원특별자치도 원주시 지정면 지정로 317", null, "2025-09-11T14:30:00", 37.3625185087, 127.8350852657, "소금산그랜드밸리", 2, "", 0, "2025-09-11T13:30:00")
                    )
                )
            ),
            lodgings = listOf(
                Lodging("11:00:00", "강원특별자치도 원주시 지정면 지정로 912-1", emptyList(), "15:00:00", null, 37.3582544689, 127.8002607578, "유알풀빌라펜션", "", 160000),
                Lodging("11:00:00", "강원특별자치도 원주시 지정면 보통로 569-16", emptyList(), "15:00:00", null, 37.3487016553, 127.8887280458, "별무인호텔", "0507-1376-4488", 160000)
            ),
            restaurants = listOf(
                Restaurant("강원특별자치도 원주시 호저면 칠봉로 700", null, 20000, 37.4500899412, 127.8891694922, "카페이야기담", "", null),
                Restaurant("강원특별자치도 원주시 호저면 칠봉로 109-127", null, 20000, 37.4066870985, 127.9145018054, "사니다카페", "", null),
                Restaurant("강원특별자치도 원주시 북원로 2852", null, 20000, 37.3979054979, 127.9530458033, "원주 치악기사식당", "033-734-2428", null),
                Restaurant("강원특별자치도 원주시 북원로 2852", null, 20000, 37.3979054979, 127.9530458033, "미가일식당", "", null),
                Restaurant("강원특별자치도 횡성군 횡성읍 횡성로 61", null, 20000, 37.4604792187, 127.9745531385, "벌나무식당", "033-342-0635", null),
                Restaurant("강원특별자치도 횡성군 횡성읍 횡성로 71", null, 20000, 37.461359667, 127.9742614715, "순대 먹거리식당", "0507-1486-2246", null)
            )
        )*/
    )

}