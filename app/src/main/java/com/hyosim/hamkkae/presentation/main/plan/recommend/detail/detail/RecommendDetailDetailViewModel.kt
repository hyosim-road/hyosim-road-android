package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.detail

import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.domain.model.CourseDetail
import com.hyosim.hamkkae.util.InfoConstants.INFO_DESCRIPTION
import com.hyosim.hamkkae.util.InfoConstants.INFO_HOURS
import com.hyosim.hamkkae.util.InfoConstants.INFO_LOCATION
import com.hyosim.hamkkae.util.InfoConstants.INFO_PARKING
import com.hyosim.hamkkae.util.InfoConstants.INFO_PHONE
import com.hyosim.hamkkae.util.InfoConstants.INFO_PRICE
import com.hyosim.hamkkae.util.InfoConstants.INFO_WEBSITE

class RecommendDetailDetailViewModel : ViewModel() {
    val courseDetailList = listOf(
        CourseDetail(
            1, "불국사", "신라 불교 문화의 정수를 보여주는 대표적인 사찰", R.drawable.image_trip,
            listOf(
                CourseDetail.Info(1, INFO_LOCATION, "경북 경주시 진현동 15-1"),
                CourseDetail.Info(2, INFO_PHONE, "054-746-9913"),
                CourseDetail.Info(3, INFO_PRICE, "성인 6,000원, 청소년 4,000원, 어린이 3,000원")
            )
        ),
        CourseDetail(
            2, "석굴암", "통일신라시대의 대표적인 석굴사원으로 국보 제24호", R.drawable.image_trip,
            listOf(
                CourseDetail.Info(4, INFO_LOCATION, "경북 경주시 불국로 873-243"),
                CourseDetail.Info(5, INFO_PHONE, "054-746-9933"),
                CourseDetail.Info(6, INFO_PRICE, "성인 6,000원, 청소년 4,000원, 어린이 3,000원"),
                CourseDetail.Info(7, INFO_HOURS, "09:00 - 17:00 (계절별 변동 가능)")
            )
        ),
        CourseDetail(
            3, "첨성대", "동양에서 가장 오래된 천문대로 국보 제31호",R.drawable.image_trip,
            listOf(
                CourseDetail.Info(8, INFO_LOCATION, "경북 경주시 인왕동 839-1"),
                CourseDetail.Info(9, INFO_HOURS, "09:00 - 22:00 (동절기 21:00까지)"),
                CourseDetail.Info(10, INFO_PRICE, "무료")
            )
        ),
        CourseDetail(
            4, "동궁과 월지 (안압지)", "통일신라시대 왕궁의 별궁터로 야경이 아름다운 곳",R.drawable.image_trip,
            listOf(
                CourseDetail.Info(11, INFO_LOCATION, "경북 경주시 원화로 102"),
                CourseDetail.Info(12, INFO_PHONE, "054-750-8655"),
                CourseDetail.Info(13, INFO_PRICE, "성인 3,000원, 청소년 2,000원, 어린이 1,000원"),
                CourseDetail.Info(14, INFO_HOURS, "09:00 - 22:00 (입장 마감 21:30)")
            )
        ),
        CourseDetail(
            5, "국립경주박물관", "신라의 역사와 문화를 한눈에 볼 수 있는 박물관",R.drawable.image_trip,
            listOf(
                CourseDetail.Info(15, INFO_LOCATION, "경북 경주시 일정로 186"),
                CourseDetail.Info(16, INFO_PHONE, "054-740-7500"),
                CourseDetail.Info(17, INFO_PRICE, "무료 (특별전 제외)"),
                CourseDetail.Info(18, INFO_HOURS, "10:00 - 18:00 (토요일 야간 개장)"),
                CourseDetail.Info(19, INFO_WEBSITE, "gyeongju.museum.go.kr")
            )
        ),
        CourseDetail(
            6, "황리단길", "전통 한옥과 현대적인 감각이 어우러진 경주의 핫플레이스",R.drawable.image_trip,
            listOf(
                CourseDetail.Info(20, INFO_LOCATION, "경북 경주시 포석로 일대 (사정동, 황남동)"),
                CourseDetail.Info(21, INFO_DESCRIPTION, "다양한 맛집, 카페, 소품샵, 한복 대여점 밀집 지역") // INFO_DESCRIPTION 추가 가정
            )
        ),
        CourseDetail(
            7, "경주월드", "다양한 놀이기구와 볼거리를 제공하는 테마파크",R.drawable.image_trip,
            listOf(
                CourseDetail.Info(22, INFO_LOCATION, "경북 경주시 보문로 544"),
                CourseDetail.Info(23, INFO_PHONE, "054-745-7700"),
                CourseDetail.Info(24, INFO_PRICE, "자유이용권 대인 48,000원 (홈페이지 할인 참고)"),
                CourseDetail.Info(25, INFO_HOURS, "10:00 - 18:00 (시즌별, 요일별 변동)"),
                CourseDetail.Info(26, INFO_PARKING, "유료 주차 가능")
            )
        ),
        CourseDetail(
            8, "양동마을", "조선시대 양반 마을의 모습을 간직한 유네스코 세계문화유산",R.drawable.image_trip,
            listOf(
                CourseDetail.Info(27, INFO_LOCATION, "경북 경주시 강동면 양동마을길 134"),
                CourseDetail.Info(28, INFO_PHONE, "054-762-6263"),
                CourseDetail.Info(29, INFO_PRICE, "성인 4,000원, 청소년/군인 2,000원, 어린이 1,500원"),
                CourseDetail.Info(30, INFO_HOURS, "하절기 09:00-18:00, 동절기 09:00-17:00")
            )
        )
    )

}