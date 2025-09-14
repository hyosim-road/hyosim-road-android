package com.hyosim.hamkkae.presentation.main.trip_detail.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.domain.model.TotalCourse
import com.hyosim.hamkkae.domain.repository.ConversationRepository
import com.hyosim.hamkkae.extension.conversation.GetAnswersState
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_HISTORY
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_TEMPLE
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
class TripDetailScheduleViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository
) : BaseViewModel() {
    private var _getAnswersState =
        MutableStateFlow<GetAnswersState>(GetAnswersState.Loading)
    val getAnswersState: StateFlow<GetAnswersState> = _getAnswersState.asStateFlow()

    fun getAnswers(tripId:Int){
        viewModelScope.launch {
            conversationRepository.getConversations(tripId).onSuccess { response ->
                _getAnswersState.value = GetAnswersState.Success(response.data!!)
            }.onFailure {
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        val apiError = parseStatusCode(errorBodyString)

                        _getAnswersState.value = GetAnswersState.Error(
                            status = apiError.status,
                            message = apiError.message,
                        )
                    } catch (e: Exception) {
                        Timber.e("Error parsing error body: $e")
                        _getAnswersState.value = GetAnswersState.Error(
                            status = null,
                            message = "알 수 없는 에러가 발생했습니다.",
                        )
                    }
                } else {
                    _getAnswersState.value = GetAnswersState .Error(
                        status = null,
                        message = it.message,
                    )
                }
            }
        }
    }

    val totalScheduleList = listOf(
        TotalCourse(1, "2025.08.01", listOf(
            TodaySchedule(1, "불국사", KEYWORD_TEMPLE, "09:00", "12:00", TYPE_COMPLETE),
            TodaySchedule(2, "석굴암", KEYWORD_HISTORY, "13:00", "16:00", TYPE_IN_PROCESS),
            TodaySchedule(3, "첨성대", KEYWORD_HISTORY, "17:00", "20:00", TYPE_BEFORE_STARTING),
            TodaySchedule(4, "동궁과 월지", KEYWORD_HISTORY, "13:00", "16:00",TYPE_BEFORE_STARTING)
        )),
        TotalCourse(2, "2025.08.02", listOf(
            TodaySchedule(1, "불국사", KEYWORD_TEMPLE, "09:00", "12:00", TYPE_BEFORE_STARTING),
            TodaySchedule(2, "석굴암", KEYWORD_HISTORY, "13:00", "16:00", TYPE_BEFORE_STARTING),
            TodaySchedule(3, "첨성대", KEYWORD_HISTORY, "17:00", "20:00", TYPE_BEFORE_STARTING),
            TodaySchedule(4, "동궁과 월지", KEYWORD_HISTORY, "13:00", "16:00",TYPE_BEFORE_STARTING)
        )),
        TotalCourse(3, "2025.08.03", listOf(
            TodaySchedule(1, "불국사", KEYWORD_TEMPLE, "09:00", "12:00", TYPE_BEFORE_STARTING),
            TodaySchedule(2, "석굴암", KEYWORD_HISTORY, "13:00", "16:00", TYPE_BEFORE_STARTING),
            TodaySchedule(3, "첨성대", KEYWORD_HISTORY, "17:00", "20:00", TYPE_BEFORE_STARTING),
            TodaySchedule(4, "동궁과 월지", KEYWORD_HISTORY, "13:00", "16:00",TYPE_BEFORE_STARTING)
        ))
    )
}