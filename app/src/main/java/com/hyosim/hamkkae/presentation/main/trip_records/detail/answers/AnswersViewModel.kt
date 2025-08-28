package com.hyosim.hamkkae.presentation.main.trip_records.detail.answers

import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.domain.model.Answer

class AnswersViewModel: ViewModel() {
    val answerList = listOf(
        Answer(
            1,
            "불국사 대웅전",
            listOf("웅장함", "신라건축", "역사", "기도"),
            "\"대웅전 앞에서 수많은 세월을 견뎌온 건축물의 아름다움에 압도되었을 때, 역사의 무게가 느껴졌어요.\""
        ),
        Answer(
            2,
            "석굴암 본존불",
            listOf("예술혼", "섬세함", "불교미술", "경건함"),
            "\"본존불의 온화하면서도 위엄 있는 표정을 마주했을 때, 신라인들의 깊은 신앙심과 예술성에 감탄했어요.\""
        ),
        Answer(
            3,
            "동궁과 월지 야경",
            listOf("황홀경", "반영", "달빛산책", "낭만"),
            "\"어둠 속에서 환하게 빛나는 누각과 연못에 비친 반영을 보며 마치 신라시대 왕궁을 거니는 듯한 환상에 빠졌을 때.\""
        ),
        Answer(
            4,
            "첨성대와 주변 고분군",
            listOf("신비로움", "과학유산", "고분", "별자리"),
            "\"밤하늘 아래 고요히 서 있는 첨성대를 보며, 옛 선조들이 별을 관측하던 모습을 상상하니 신비로운 기분이 들었어요.\""
        ),
        Answer(
            5,
            "황리단길 골목",
            listOf("아기자기함", "한옥카페", "레트로감성", "산책"),
            "\"오래된 한옥을 개조한 예쁜 가게들과 맛집들 사이를 걸으며, 과거와 현재가 공존하는 독특한 분위기에 매료되었을 때.\""
        ),
        Answer(
            6,
            "경주국립박물관 성덕대왕신종",
            listOf("에밀레종", "종소리", "전설", "문화재"),
            "\"실제로 마주한 에밀레종의 거대한 크기와 그 안에 담긴 슬픈 전설을 떠올리며 깊은 여운을 느꼈을 때.\""
        )
    )
}