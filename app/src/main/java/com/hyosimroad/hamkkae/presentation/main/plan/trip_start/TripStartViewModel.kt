package com.hyosimroad.hamkkae.presentation.main.plan.trip_start

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.TodaySchedule
import com.hyosimroad.hamkkae.util.KeywordConstants.KEYWORD_HISTORY
import com.hyosimroad.hamkkae.util.KeywordConstants.KEYWORD_TEMPLE
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_IN_PROCESS

class TripStartViewModel: ViewModel() {
    val previewList = listOf(
        TodaySchedule(1, "불국사", KEYWORD_TEMPLE, "09:00", "12:00", TYPE_COMPLETE),
        TodaySchedule(2, "석굴암", KEYWORD_HISTORY, "13:00", "16:00", TYPE_IN_PROCESS),
        TodaySchedule(3, "첨성대", KEYWORD_HISTORY, "17:00", "20:00", TYPE_BEFORE_STARTING),
        TodaySchedule(4, "동궁과 월지", KEYWORD_HISTORY, "13:00", "16:00",TYPE_BEFORE_STARTING)
    )
}