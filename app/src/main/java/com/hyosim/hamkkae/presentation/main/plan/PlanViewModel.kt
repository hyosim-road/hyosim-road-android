package com.hyosim.hamkkae.presentation.main.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.domain.repository.AiPlanRepository
import com.hyosim.hamkkae.domain.repository.PlanRepository
import com.hyosim.hamkkae.extension.plan.AiCourseRecommendState
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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planRepository: PlanRepository,
    private val aiPlanRepository: AiPlanRepository
) : BaseViewModel() {
    // 날짜
    private val _departureDate = MutableLiveData<String>()
    val departureDate: LiveData<String> = _departureDate

    private val _arrivalDate = MutableLiveData<String>()
    val arrivalDate: LiveData<String> = _arrivalDate

    // 인원 수
    private val _numberOfPeople = MutableLiveData(0)
    val numberOfPeople: LiveData<Int> = _numberOfPeople

    // 예산
    private val _budgetRange = MutableLiveData<String>()
    val budgetRange: LiveData<String> = _budgetRange

    // 여행 스타일
    private val _travelStyle = MutableLiveData<String>()
    val travelStyle: LiveData<String> = _travelStyle

    private var _courseRecommendState =
        MutableStateFlow<CourseRecommendState>(CourseRecommendState.Loading)
    val courseRecommendState: StateFlow<CourseRecommendState> = _courseRecommendState.asStateFlow()

    private var _aiCourseRecommendState =
        MutableStateFlow<AiCourseRecommendState>(AiCourseRecommendState.Loading)
    val aiCourseRecommendState: StateFlow<AiCourseRecommendState> = _aiCourseRecommendState.asStateFlow()


    fun setDepartureDate(date: String) {
        _departureDate.value = date
    }

    fun setArrivalDate(date: String) {
        _arrivalDate.value = date
    }

    fun increasePeople() {
        _numberOfPeople.value = (_numberOfPeople.value ?: 0) + 1
    }

    fun decreasePeople() {
        val current = _numberOfPeople.value ?: 0
        if (current > 0) {
            _numberOfPeople.value = current - 1
        }
    }

    fun setBudgetRange(budget: String) {
        _budgetRange.value = budget
    }

    fun setTravelStyle(style: String) {
        _travelStyle.value = style
    }

    fun toRequestDto(): CourseRecommendRequestDto {
        return CourseRecommendRequestDto(
            departureDate = _departureDate.value.orEmpty(),
            arrivalDate = _arrivalDate.value.orEmpty(),
            numberOfPeople = _numberOfPeople.value ?: 0,
            budgetRange = _budgetRange.value.orEmpty(),
            travelStyle = _travelStyle.value.orEmpty()
        )
    }

    fun recommendCourse() {
        viewModelScope.launch {
            planRepository.recommendCourse(toRequestDto()).onSuccess { response ->
                _courseRecommendState.value = CourseRecommendState.Success(response.data!!)
            }.onFailure {
                _courseRecommendState.value = CourseRecommendState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _courseRecommendState.emit(CourseRecommendState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _courseRecommendState.emit(CourseRecommendState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }

    }

    fun aiRecommendCourse(){
        viewModelScope.launch {
            aiPlanRepository.recommendCourse(toRequestDto()).onSuccess { response->
                _aiCourseRecommendState.value= AiCourseRecommendState.Success(response)
            }.onFailure {throwable ->
                when (throwable) {
                    is HttpException -> {
                        try {
                            val errorBody = throwable.response()?.errorBody()?.string().orEmpty()
                            parseHttpError(errorBody)
                        } catch (e: Exception) {
                            Timber.e(e, "Error parsing error body")
                            _aiCourseRecommendState.value = AiCourseRecommendState.Error("에러 응답 파싱 실패")
                        }
                    }
                    is IOException -> {
                        Timber.e(throwable, "네트워크 I/O 오류")
                        _aiCourseRecommendState.value = AiCourseRecommendState.Error("네트워크 오류: ${throwable.message}")
                    }
                    else -> {
                        Timber.e(throwable, "알 수 없는 오류")
                        _aiCourseRecommendState.value = AiCourseRecommendState.Error("알 수 없는 오류: ${throwable.message}")
                    }
                }
            }
        }
    }

    fun resetState(){
        _aiCourseRecommendState.value= AiCourseRecommendState.Loading
    }
}