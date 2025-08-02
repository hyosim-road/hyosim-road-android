package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.tip

import androidx.lifecycle.ViewModel

class RecommendDetailTipViewModel: ViewModel() {
    val cautionList = listOf(
        "걷는 속도를 부모님께 맞춰주세요",
        "중간중간 휴식 시간을 충분히 가져주세요",
        "계단이 많은 곳은 미리 안내해드리세요"
    )

    val bestVisitList = listOf(
        "봄(4~5월): 벚꽃이 아름다운 시기, 날씨가 온화함",
        "가을(9~11월): 단풍이 아름답고 선선한 날씨"
    )

    val checklist = listOf(
        "편한 신발", "물병", "카메라", "모자/선글라스", "간식", "휴대용 의자"
    )
}