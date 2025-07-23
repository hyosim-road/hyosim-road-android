package com.hyosimroad.hamkkae.presentation.main

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Recent
import com.hyosimroad.hamkkae.domain.model.TripRecord
import com.hyosimroad.hamkkae.util.RecentConstants.ANSWER_COMPLETE
import com.hyosimroad.hamkkae.util.RecentConstants.PHOTO_UPLOAD
import com.hyosimroad.hamkkae.util.RecentConstants.TRIP_COMPLETE
import com.hyosimroad.hamkkae.util.RecentConstants.TRIP_START
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_COMPLETE

class MainViewModel: ViewModel() {
    val recentActivityList = listOf(
        Recent(1, TRIP_COMPLETE, "경주 여행을 성공적으로 완료했어요!", 0),
  /*      Recent(2, PHOTO_UPLOAD, "동궁과 월지에서 가족사진 업로드", 2),
        Recent(3, ANSWER_COMPLETE, "마지막 질문에 답변 완료", 180),
        Recent(3, TRIP_START, "경주 여행 시작!", 200),
        Recent(3, TRIP_COMPLETE, "부산 여행을 성공적으로 완료했어요!", 300),*/
    )

    val tripRecordList = listOf(
        TripRecord(1, "제주 자연 힐링 여행", "2023.12.20", "2023.12.23", TYPE_COMPLETE),
        /*TripRecord(2, "부산 바다 여행", "2023.12.20", "2023.12.23", TYPE_COMPLETE),
        TripRecord(3, "경주 여행", "2022.12.20", "2023.12.23", TYPE_COMPLETE),
        TripRecord(4, "대전 여행", "2021.12.20", "2023.12.23", TYPE_COMPLETE),*/
    )
}