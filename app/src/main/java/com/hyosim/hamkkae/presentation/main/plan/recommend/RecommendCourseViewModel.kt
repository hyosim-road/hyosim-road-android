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
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RecommendCourseViewModel @Inject constructor(
    private val planRepository: PlanRepository,
) : BaseViewModel() {
    private var _registerState =
        MutableStateFlow<RegisterState>(RegisterState.Loading)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun register(planData: CourseRecommendRequestDto, course: AiCourseRecommendResponseDto) {
        val request = toRequestDto(planData, course)

        viewModelScope.launch {
            planRepository.register(request).onSuccess { response ->
                _registerState.value = RegisterState.Success(course)
            }.onFailure { throwable ->
                // 실패 공통 기록
                _registerState.value = RegisterState.Error("등록 실패: ${throwable.message}")

                when (throwable) {
                    is HttpException -> {
                        try {
                            val errorBody = throwable.response()?.errorBody()?.string().orEmpty()
                            parseHttpError(errorBody)
                        } catch (e: Exception) {
                            Timber.e(e, "Error parsing error body")
                            _registerState.value = RegisterState.Error("에러 응답 파싱 실패")
                        }
                    }
                    is IOException -> {
                        Timber.e(throwable, "네트워크 I/O 오류")
                        _registerState.value = RegisterState.Error("네트워크 오류: ${throwable.message}")
                    }
                    else -> {
                        Timber.e(throwable, "알 수 없는 오류")
                        _registerState.value = RegisterState.Error("알 수 없는 오류: ${throwable.message}")
                    }
                }
            }
        }
    }

    private fun toRequestDto(
        planData: CourseRecommendRequestDto,
        course: AiCourseRecommendResponseDto
    ): CourseRecommendResponseData {
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

    val mockJson = """
[{"caption":"강원, 화산체험 및 야","itinerary":[{"attractions":[{"address":"강원특별자치도 삼척시 가곡면 가곡천로 1476","contentId":"3534460","contentid":"3534460","description":null,"endTime":"2025-09-14T12:00:00","image":"http://tong.visitkorea.or.kr/cms/resource/73/3533173_image2_1.jpg","latitude":37.1501190022,"longitude":129.2059852285,"name":"가곡유황족욕체험장","order":1,"phone":"","priceKrw":0,"startTime":"2025-09-14T10:00:00"},{"address":"강원특별자치도 태백시 태백로 283-29","contentId":"126732","contentid":"126732","description":null,"endTime":"2025-09-14T14:30:00","image":"http://tong.visitkorea.or.kr/cms/resource/19/3422519_image2_1.jpg","latitude":37.2089321328,"longitude":128.9418875782,"name":"용연동굴 (강원고생대 국가지질공원)","order":2,"phone":"","priceKrw":0,"startTime":"2025-09-14T13:30:00"}],"day":"2025-09-14"},{"attractions":[{"address":"강원특별자치도 태백시 창죽동","contentId":"126840","contentid":"126840","description":null,"endTime":"2025-09-15T12:00:00","image":"http://tong.visitkorea.or.kr/cms/resource/97/2917597_image2_1.JPG","latitude":37.2306100727,"longitude":128.935454166,"name":"검룡소 (강원고생대 국가지질공원)","order":1,"phone":"","priceKrw":0,"startTime":"2025-09-15T10:00:00"},{"address":"강원특별자치도 정선군 화암면 몰운리","contentId":"125666","contentid":"125666","description":null,"endTime":"2025-09-15T14:30:00","image":"http://tong.visitkorea.or.kr/cms/resource/58/3505558_image2_1.jpg","latitude":37.3243522572,"longitude":128.8011272522,"name":"소금강 (강원고생대 국가지질공원)","order":2,"phone":"","priceKrw":0,"startTime":"2025-09-15T13:30:00"}],"day":"2025-09-15"}],"lodgings":[{"CheckOut":"11:00:00","address":"강원특별자치도 정선군 사북읍 소금강로 3489","amenities":[],"checkIn":"15:00:00","contentId":"139224","contentid":"139224","description":null,"image":"http://tong.visitkorea.or.kr/cms/resource/20/207320_image2_1.jpg","latitude":37.2294806274,"longitude":128.8420903297,"name":"갤럭시 모텔","phone":"033-592-9923","pricePerNightKrw":80000},{"CheckOut":"11:00:00","address":"강원특별자치도 정선군 고한읍 고한2길 52","amenities":[],"checkIn":"15:00:00","contentId":"625996","contentid":"625996","description":null,"image":"http://tong.visitkorea.or.kr/cms/resource/86/601586_image2_1.jpg","latitude":37.202675557,"longitude":128.8515597322,"name":"리치모텔","phone":"033-592-3241","pricePerNightKrw":80000}],"restaurants":[{"address":"강원특별자치도 태백시 구와우길 49-1","contentId":"2925173","contentid":"2925173","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/70/2925170_image2_1.jpg","latitude":37.20786923,"longitude":128.9866336113,"name":"구와우순두부식당","phone":"","signatureMenu":null},
{"address":"강원특별자치도 태백시 상장3길 26","contentId":"2892457","contentid":"2892457","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/47/2892447_image2_1.jpg","latitude":37.1539838308,"longitude":128.9907154278,"name":"신가네식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 태백시 상장남길 64","contentId":"628507","contentid":"628507","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.1479428516,"longitude":128.9904045257,"name":"장인식당","phone":"033-553-5252","signatureMenu":null},{"address":"강원특별자치도 태백시 시장북길 9 (황지동)","contentId":"2935603","contentid":"2935603","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.1734094067,"longitude":128.9922063755,"name":"현대실비식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 태백시 대학길 35","contentId":"681652","contentid":"681652","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/67/685767_image2_1.jpg","latitude":37.1588025643,"longitude":128.9825437897,"name":"태백한우골실비식당","phone":"033-554-4599","signatureMenu":null},{"address":"강원특별자치도 태백시 감천로 4","contentId":"1819042","contentid":"1819042","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.1589692907,"longitude":128.9866239759,"name":"원조태성실비식당","phone":"033-552-5287","signatureMenu":null}]},
{"caption":"1. 강릉 허균 기념공","itinerary":[{"attractions":[{"address":"강원특별자치도 강릉시 난설헌로193번길 1-16 (초당동)","contentId":"2773086","contentid":"2773086","description":null,"endTime":"2025-09-14T12:00:00","image":"http://tong.visitkorea.or.kr/cms/resource/11/3493211_image2_1.jpg","latitude":37.7917948539,"longitude":128.9097365341,"name":"허균·허난설헌 기념공원","order":1,"phone":"","priceKrw":0,"startTime":"2025-09-14T10:00:00"},{"address":"강원특별자치도 강릉시 난설헌로 131 (초당동)","contentId":"2465063","contentid":"2465063","description":null,"endTime":"2025-09-14T14:30:00","image":"http://tong.visitkorea.or.kr/cms/resource/33/3072433_image2_1.jpg","latitude":37.7879702339,"longitude":128.906528608,"name":"강릉 녹색도시체험센터","order":2,"phone":"","priceKrw":0,"startTime":"2025-09-14T13:30:00"}],"day":"2025-09-14"},{"attractions":[{"address":"강원특별자치도 강릉시 난설헌로 131 (초당동)","contentId":"3507377","contentid":"3507377","description":null,"endTime":"2025-09-15T12:00:00","image":"http://tong.visitkorea.or.kr/cms/resource/96/3507296_image2_1.jpg","latitude":37.7879702339,"longitude":128.906528608,"name":"강릉 메타버스 체험관","order":1,"phone":"","priceKrw":0,"startTime":"2025-09-15T10:00:00"},{"address":"강원특별자치도 강릉시 노암동","contentId":"3022373","contentid":"3022373","description":null,"endTime":"2025-09-15T14:30:00","image":"http://tong.visitkorea.or.kr/cms/resource/05/3022205_image2_1.jpg","latitude":37.7480551515,"longitude":128.8934282457,"name":"강릉 남산공원","order":2,"phone":"","priceKrw":0,"startTime":"2025-09-15T13:30:00"}],"day":"2025-09-15"}],
"lodgings":[{"CheckOut":"11:00:00","address":"강원특별자치도 강릉시 중앙시장2길 10-6 (성남동)","amenities":[],"checkIn":"15:00:00","contentId":"3532464","contentid":"3532464","description":null,"image":null,"latitude":37.7515992406,"longitude":128.8962528812,"name":"올인모텔","phone":"","pricePerNightKrw":80000},{"CheckOut":"11:00:00","address":"강원특별자치도 강릉시 중앙시장3길 23 (성남동)","amenities":[],"checkIn":"15:00:00","contentId":"3469420","contentid":"3469420","description":null,"image":null,"latitude":37.7518992274,"longitude":128.8973277759,"name":"K게스트하우스","phone":"","pricePerNightKrw":80000}],
"restaurants":[{"address":"강원특별자치도 강릉시 해안로 298-5 (강문동)","contentId":"3532991","contentid":"3532991","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.7921227817,"longitude":128.9193634337,"name":"다모아식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 강릉시 강릉대로587번길 2","contentId":"2868923","contentid":"2868923","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/10/2868910_image2_1.jpg","latitude":37.7844431324,"longitude":128.920291366,"name":"강릉유환식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 강릉시 해안로 517","contentId":"2874075","contentid":"2874075","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/74/2874074_image2_1.jpg","latitude":37.8046182489,"longitude":128.903528362,"name":"태백산맥식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 강릉시 경강로 2403 (송정동)","contentId":"2871178","contentid":"2871178","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/62/2871162_image2_1.jpg","latitude":37.7724973406,"longitude":128.922744293,"name":"광덕식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 강릉시 강릉대로223번길 11 (교동)","contentId":"3533002","contentid":"3533002","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.7597927128,"longitude":128.8949666333,"name":"정선 이모네 식당","phone":"0507-1430-5564","signatureMenu":null},{"address":"강원특별자치도 강릉시 금성로23번길 4","contentId":"1855941","contentid":"1855941","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.7537406956,"longitude":128.8980693184,"name":"여왕개미식당","phone":"033-645-2919","signatureMenu":null}]},
{"caption":"강릉 자연 문화 공원,","itinerary":[{"attractions":[{"address":"강원특별자치도 홍천군 내촌면 동창로 282","contentId":"2715231","contentid":"2715231","description":null,"endTime":"2025-09-14T12:00:00","image":"http://tong.visitkorea.or.kr/cms/resource/47/3384147_image2_1.JPG","latitude":37.7820176498,"longitude":128.1584075026,"name":"찡야산 문화수목원","order":1,"phone":"","priceKrw":0,"startTime":"2025-09-14T10:00:00"},{"address":"강원특별자치도 홍천군 영귀미면 덕치리","contentId":"2638580","contentid":"2638580","description":null,"endTime":"2025-09-14T14:30:00","image":"http://tong.visitkorea.or.kr/cms/resource/36/3494136_image2_1.jpg","latitude":37.6945544662,"longitude":127.9522204697,"name":"수타사 농촌테마공원","order":2,"phone":"","priceKrw":0,"startTime":"2025-09-14T13:30:00"}],"day":"2025-09-14"},
{"attractions":[{"address":"강원특별자치도 홍천군 홍천읍 산림공원2길 31","contentId":"3036761","contentid":"3036761","description":null,"endTime":"2025-09-15T12:00:00","image":"http://tong.visitkorea.or.kr/cms/resource/41/3036741_image2_1.jpg","latitude":37.6899122729,"longitude":127.8953257659,"name":"도시산림공원토리숲","order":1,"phone":"","priceKrw":0,"startTime":"2025-09-15T10:00:00"},{"address":"강원특별자치도 횡성군 횡성읍 문예로 75","contentId":"3072043","contentid":"3072043","description":null,"endTime":"2025-09-15T14:30:00","image":"http://tong.visitkorea.or.kr/cms/resource/52/3052552_image2_1.jpg","latitude":37.4907202341,"longitude":127.9782824672,"name":"횡성문화체육공원","order":2,"phone":"","priceKrw":0,"startTime":"2025-09-15T13:30:00"}],"day":"2025-09-15"}],
"lodgings":[{"CheckOut":"11:00:00","address":"강원특별자치도 횡성군 횡성읍 앞들남로 7","amenities":[],"checkIn":"15:00:00","contentId":"496377","contentid":"496377","description":null,"image":"http://tong.visitkorea.or.kr/cms/resource/21/1816221_image2_1.jpg","latitude":37.4856820511,"longitude":127.9848570217,"name":"오즈모텔","phone":"033-345-4100","pricePerNightKrw":80000},{"CheckOut":"11:00:00","address":"강원특별자치도 원주시 소초면 백교길 107","amenities":[],"checkIn":"15:00:00","contentId":"2692119","contentid":"2692119","description":null,"image":null,"latitude":37.4259710861,"longitude":128.0690483979,"name":"클라우드펜션민박","phone":"","pricePerNightKrw":80000}],
"restaurants":[{"address":"강원특별자치도 홍천군 구룡령로 214-3","contentId":"2892255","contentid":"2892255","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/40/2892240_image2_1.jpeg","latitude":37.7544143806,"longitude":127.962526089,"name":"길매식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 인제군 남면 삼팔선로 2115","contentId":"134189","contentid":"134189","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/43/1813943_image2_1.jpg","latitude":37.9675445681,"longitude":128.0776481269,"name":"정원식당","phone":"033-461-5080","signatureMenu":null},{"address":"강원특별자치도 홍천군 홍천읍 진리로 20","contentId":"134332","contentid":"134332","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/75/1814075_image2_1.jpg","latitude":37.6924424224,"longitude":127.8893151246,"name":"삼오식당","phone":"033-434-2435","signatureMenu":null},{"address":"강원특별자치도 홍천군 북방면 홍천로 119","contentId":"2837965","contentid":"2837965","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/58/2837958_image2_1.jpg","latitude":37.6949082643,"longitude":127.8605009985,"name":"일송식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 홍천군 홍천읍 진리로 13-5","contentId":"2807694","contentid":"2807694","description":null,"estimatedCostPerPersonKrw":12000,"image":null,"latitude":37.7012934895,"longitude":127.8527376253,"name":"홍천한우사랑말식당","phone":"","signatureMenu":null},{"address":"강원특별자치도 홍천군 홍천읍 홍천로 666-1","contentId":"2913270","contentid":"2913270","description":null,"estimatedCostPerPersonKrw":12000,"image":"http://tong.visitkorea.or.kr/cms/resource/80/2919280_image2_1.jpg","latitude":37.7070022319,"longitude":127.9100350364,"name":"카페 까만콩","phone":"0507-1314-1764","signatureMenu":null}]}]
""".trimIndent()

    val mockData: List<AiCourseRecommendResponseDto> =
        Json.decodeFromString(mockJson)

}