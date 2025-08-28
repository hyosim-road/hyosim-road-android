package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview

import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.domain.model.HighLights
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_HISTORY
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_TEMPLE
import com.hyosim.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosim.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosim.hamkkae.util.StateConstants.TYPE_IN_PROCESS

class RecommendDetailOverviewViewModel: ViewModel() {
    val highlightsList = listOf(
        HighLights(1, "유네스코 세계문화유산 불국사, 석굴암 방문"),
        HighLights(2, "신라 왕궁의 흔적 동궁과 월지 야경 감상"),
        HighLights(3, "천년의 지혜가 담긴 첨성대에서 역사 체험"),
        HighLights(4, "경주 전통시장에서 향토음식 맛보기"),
    )

    val previewList = listOf(
        TodaySchedule(1, "불국사", KEYWORD_TEMPLE, "09:00", "12:00", TYPE_COMPLETE),
        TodaySchedule(2, "석굴암", KEYWORD_HISTORY, "13:00", "16:00", TYPE_IN_PROCESS),
        TodaySchedule(3, "첨성대", KEYWORD_HISTORY, "17:00", "20:00", TYPE_BEFORE_STARTING),
        TodaySchedule(4, "동궁과 월지", KEYWORD_HISTORY, "13:00", "16:00",TYPE_BEFORE_STARTING)
    )
}