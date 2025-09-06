package com.hyosim.hamkkae.presentation.main.plan.recommend

import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.domain.model.Course

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

    val mockCourses: List<AiCourseRecommendResponseDto> = listOf(
        // --------------------- 첫 번째 코스 ---------------------
        AiCourseRecommendResponseDto(
            itinerary = listOf(
                AiCourseRecommendResponseDto.Itinerary(
                    day = "2025-09-10",
                    attractions = listOf(
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 강릉시 성산면 삼포암길 133",
                            description = null,
                            endTime = "2025-09-10T12:00:00",
                            latitude = 37.7149097631,
                            longitude = 128.7905413133,
                            name = "국립대관령자연휴양림",
                            order = 1,
                            phone = "",
                            priceKrw = 0,
                            startTime = "2025-09-10T10:00:00"
                        ),
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 평창군 대관령면 대관령마루길 483-32",
                            description = null,
                            endTime = "2025-09-10T14:30:00",
                            latitude = 37.6876427505,
                            longitude = 128.7533288363,
                            name = "대관령양떼목장",
                            order = 2,
                            phone = "",
                            priceKrw = 0,
                            startTime = "2025-09-10T13:30:00"
                        )
                    )
                ),
                AiCourseRecommendResponseDto.Itinerary(
                    day = "2025-09-11",
                    attractions = listOf(
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 강릉시 성산면 어흘리",
                            description = null,
                            endTime = "2025-09-11T12:00:00",
                            latitude = 37.7099545878,
                            longitude = 128.7806256921,
                            name = "제왕산/능경봉",
                            order = 1,
                            phone = "",
                            priceKrw = 0,
                            startTime = "2025-09-11T10:00:00"
                        ),
                        AiCourseRecommendResponseDto.Itinerary.Attraction(
                            address = "강원특별자치도 평창군 대관령로 94 대관령 원예농협",
                            description = null,
                            endTime = "2025-09-11T14:30:00",
                            latitude = 37.6726296948,
                            longitude = 128.7066362804,
                            name = "바우파머스몰(대관령마켓)",
                            order = 2,
                            phone = "010-7742-7808",
                            priceKrw = 0,
                            startTime = "2025-09-11T13:30:00"
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
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 평창군 대관령면 대관령마루길 347", null, 20000, 37.685315007, 128.7412077776, "남경식당", "033-335-5891", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 강릉시 범일로579번길 59", null, 20000, 37.735625703, 128.8764607133, "청송식당", "010-9057-9252", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 평창군 대관령면 송천3길 10", null, 20000, 37.6714188107, 128.7111331341, "넘버나인정육식당", "", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 평창군 대관령면 횡계2길 11-1", null, 20000, 37.673293553, 128.7090166011, "남원식당", "", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 평창군 대관령면 횡계길 24", null, 20000, 37.6732963253, 128.7087471805, "수미카페", "", null)
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
                AiCourseRecommendResponseDto.Lodging("11:00:00", "강원특별자치도 속초시 설악산로836번길 6", emptyList(), "15:00:00", null, 38.1684962792, 128.5171325821, "뉴스타트설악리조트[한국관광 품질인증/Korea Quality]", "033-637-2239", 160000)
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
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 양양군 서면 설악로 1417-7", null, 20000, 38.076131755, 128.5032672614, "물레방아식당", "", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 양양군 낙산사로 37-2", null, 20000, 38.120976219, 128.6299238227, "전라도식당", "", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 속초시 먹거리4길 21", null, 20000, 38.1957108631, 128.5730322116, "완도회식당", "", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 속초시 엑스포로 10-3", null, 20000, 38.1915116955, 128.5907196256, "프리미엄 이가한우 정육참숯식당", "", null),
                AiCourseRecommendResponseDto.Restaurant("강원특별자치도 속초시 중앙로108번길 22-1", null, 20000, 38.201027209, 128.5883667641, "후포식당", "", null)
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