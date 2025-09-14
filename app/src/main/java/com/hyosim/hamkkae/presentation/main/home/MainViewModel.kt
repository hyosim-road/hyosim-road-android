package com.hyosim.hamkkae.presentation.main.home

import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.domain.model.Album.Photo
import com.hyosim.hamkkae.domain.model.Recent
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.domain.model.TripRecord
import com.hyosim.hamkkae.domain.repository.ConversationRepository
import com.hyosim.hamkkae.domain.repository.HomeRepository
import com.hyosim.hamkkae.extension.conversation.GetQuestionState
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
    private val homeRepository: HomeRepository,
    private val conversationRepository: ConversationRepository
): BaseViewModel() {
    private var _progressTripState =
        MutableStateFlow<ProgressTripState>(ProgressTripState.Loading)
    val progressTripState: StateFlow<ProgressTripState> = _progressTripState.asStateFlow()

    private var tripId:Int? = null

    private var _getQuestionState =
        MutableStateFlow<GetQuestionState>(GetQuestionState.Loading)
    val getQuestionState: StateFlow<GetQuestionState> = _getQuestionState.asStateFlow()

    fun progressTrip(){
        viewModelScope.launch {
            homeRepository.progressTrip().onSuccess { response->
                /*val original = response.data!!

                // 오늘 날짜 (yyyy-MM-dd)
                val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA).format(System.currentTimeMillis())
                val tomorrow = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA).format(System.currentTimeMillis() + 24 * 60 * 60 * 1000)

                // itinerary 날짜만 교체 (Day 1 = 오늘, Day 2 = 내일)
                val updatedItinerary = original.itinerary.mapIndexed { index, day ->
                    val newDay = if (index == 0) today else tomorrow
                    day.copy(
                        day = newDay,
                        attractions = day.attractions.mapIndexed { attrIndex, attr ->
                            if (attrIndex == 1) {
                                // 두 번째 attraction만 시간 교체
                                attr.copy(
                                    startTime = "${newDay}T13:00:00",
                                    endTime = "${newDay}T13:30:00"
                                )
                            } else {
                                // 나머지는 기존 시간 유지 (날짜만 교체)
                                val startTime = attr.startTime.substring(11) // "HH:mm:ss"
                                val endTime = attr.endTime.substring(11)
                                attr.copy(
                                    startTime = "${newDay}T$startTime",
                                    endTime = "${newDay}T$endTime"
                                )
                            }
                        }
                    )
                }

                val updatedCourse = original.copy(itinerary = updatedItinerary)

                _progressTripState.value = ProgressTripState.Success(updatedCourse)*/
                tripId = response.data!!.id
                _progressTripState.value= ProgressTripState.Success(response.data!!)
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

    fun getQuestion(){
        viewModelScope.launch {
            conversationRepository.getQuestion(tripId!!).onSuccess { response->
                _getQuestionState.value= GetQuestionState.Success(response.data!!)
            }.onFailure {
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        val apiError = parseStatusCode(errorBodyString)

                        _getQuestionState.value = GetQuestionState.Error(
                            status = apiError.status,
                            message = apiError.message,
                        )
                    } catch (e: Exception) {
                        Timber.e("Error parsing error body: $e")
                        _getQuestionState.value = GetQuestionState.Error(
                            status = null,
                            message = "알 수 없는 에러가 발생했습니다.",
                        )
                    }
                } else {
                    _getQuestionState.value = GetQuestionState.Error(
                        status = null,
                        message = it.message,
                    )
                }
            }
        }
    }
}