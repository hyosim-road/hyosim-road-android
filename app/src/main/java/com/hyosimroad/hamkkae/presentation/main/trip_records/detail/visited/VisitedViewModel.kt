package com.hyosimroad.hamkkae.presentation.main.trip_records.detail.visited

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Album.Photo
import com.hyosimroad.hamkkae.domain.model.TodaySchedule
import com.hyosimroad.hamkkae.domain.model.TotalCourse
import com.hyosimroad.hamkkae.domain.model.TripRecord
import com.hyosimroad.hamkkae.util.KeywordConstants.KEYWORD_HISTORY
import com.hyosimroad.hamkkae.util.KeywordConstants.KEYWORD_TEMPLE
import com.hyosimroad.hamkkae.util.StateConstants
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_IN_PROCESS

class VisitedViewModel: ViewModel() {
    val visitedList = listOf(
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