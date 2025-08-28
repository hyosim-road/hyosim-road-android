package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.restaurant

import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.domain.model.Info

class RecommendDetailInfoViewModel : ViewModel() {
    val restaurantList = listOf(
        Info(
            1,
            "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nqPfRslmQlYGxmAygDLRQVkrvl2Fd9mAD36cAmAyXuhshc_H06EW3Jvb63N30EQDzQEWDnXbU5j2dR69kwd5vpFO3CbECOfrigp-V5sM7__WiwRla29VKjErtjWJ1r-jPU1a2YZ=s1360-w1360-h1020-rw",
            "한정식",
            "경주 한정식 명가",
            "2~3만원",
            "경상북도 경주시 교동",
            "50년 전통의 경주 향토음식 전문점으로 부모님 세대가 좋아하실 깔끔한 한정식을 제공합니다.",
            null,
            null,
            listOf("경주 쌈밥", "전복죽", "한우불고기"),
            listOf("부모님 추천", "주차 가능", "룸 있음")
        ),
        Info(
            2,
            "https://mblogthumb-phinf.pstatic.net/MjAyMTA5MjFfMjY3/MDAxNjMyMjA5MzA2Mzky.HIfZFAa5VhGblZqIap9dZlrdtK3Pr_844zizBPkP6c4g.-0Yyz0YVB50LjRiWV-MUWAw3GWoDJuEOf1IMQoJLs_Ag.JPEG.rlaalswn9602/1_(1).jpg?type=w800",
            "양식",
            "황리단길 파스타 맛집",
            "파스타 15,000원 ~ 20,000원",
            "경상북도 경주시 황남동 567-89",
            "황리단길에 위치한 아늑한 분위기의 이탈리안 레스토랑. 신선한 재료로 만든 파스타와 리조또가 인기입니다.",
            null,
            null,
            listOf("해산물 토마토 파스타", "버섯 크림 리조또", "고르곤졸라 피자", "수제 티라미수"),
            listOf("데이트 코스", "분위기 좋은", "반려동물 동반 가능(야외석)", "예약 권장")
        ),
        Info(
            3,
            "https://www.korea.kr/newsWeb/resources/attaches/2015.04/09/20150409110619109_L12DQ727.jpg",
            "한식",
            "교리 쌈밥집",
            "쌈밥 정식 1인 13,000원",
            "경상북도 경주시 교동 234-56",
            "다양한 종류의 신선한 쌈 채소와 함께 돼지불고기, 우렁쌈장 등을 즐길 수 있는 건강한 쌈밥 전문점입니다.",
            null,
            null,
            listOf("돼지불고기 쌈밥", "우렁쌈장", "해물된장찌개", "도토리묵"),
            listOf("가성비 좋음", "단체 가능", "신선한 채소", "주차 공간 협소")
        ),
        Info(
            4,
            "https://media.triple.guide/triple-cms/c_limit,f_auto,h_1024,w_1024/a650f443-4ae5-46d0-8a7a-bda7e2eadf8a.jpeg",
            "카페/디저트",
            "보문호수 전망 좋은 카페",
            "커피 5,000원~, 디저트 7,000원~",
            "경상북도 경주시 신평동 345-67",
            "보문호수가 한눈에 내려다보이는 아름다운 전망을 자랑하는 카페. 다양한 음료와 수제 디저트를 즐기며 휴식을 취할 수 있습니다.",
            null,
            null,
            listOf("아인슈페너", "수제 과일청 에이드", "딸기 생크림 케이크", "크로플"),
            listOf("전망 좋은", "사진찍기 좋은 곳", "데이트 추천", "주차 편리")
        ),
        Info(
            5,
            "https://image.withstatic.com/204/236/116/c86751e48b9f4aa494f237fbb3b0d819.jpg",
            "전통찻집",
            "고즈넉한 찻집, 수오재",
            "전통차 7,000원~, 다과 5,000원~",
            "경상북도 경주시 배반동 456-78",
            "오래된 한옥을 개조하여 만든 전통 찻집으로, 조용하고 아늑한 분위기에서 다양한 전통차와 다과를 맛볼 수 있습니다.",
            null,
            null,
            listOf("쌍화차", "대추차", "오미자차", "한과 세트", "약과"),
            listOf("조용한 분위기", "한옥 체험", "힐링 공간", "좌식 테이블")
        ),
        Info(
            6,
            "https://www.gyeongju.go.kr/upload/content/thumb/20191129/81B84F8173EE44CCB5BECE863CE5564C.jpg",
            "분식/시장음식",
            "성동시장 먹거리 골목",
            "떡볶이 3,000원, 김밥 2,500원 등",
            "경상북도 경주시 성동동 56-78 (성동시장 내)",
            "경주 현지인들이 즐겨 찾는 성동시장 내 먹거리 골목. 저렴한 가격에 다양한 길거리 음식과 분식을 맛볼 수 있습니다.",
            null,
            null,
            listOf("우엉김밥", "빨간오뎅", "잔치국수", "순대", "튀김"),
            listOf("현지인 맛집", "가성비 최고", "다양한 메뉴", "시장 구경")
        ),
        Info(
            7,
            "https://media-cdn.tripadvisor.com/media/photo-s/19/5f/00/79/2.jpg",
            "고기/구이",
            "한우 숯불구이 전문점",
            "한우 등심 1인분(150g) 35,000원~",
            "경상북도 경주시 용강동 678-90",
            "최상급 한우를 참숯에 직접 구워 먹는 맛집. 부드러운 육질과 풍부한 육즙을 자랑합니다.",
            null,
            null,
            listOf("한우 생등심", "한우 갈비살", "육회", "된장찌개", "냉면"),
            listOf("회식 장소 추천", "고급스러운", "주차 가능", "예약 권장")
        ),
        Info(
            8,
            "https://onlmenu.com/data/editor/2506/c8d855fdc0b8550ddf10ae595091dc45_1749197983_2478.jpg",
            "면요리",
            "경주 밀면 맛집",
            "밀면 8,000원, 비빔밀면 8,500원",
            "경상북도 경주시 노동동 789-01",
            "더운 여름철 시원하게 즐길 수 있는 경주식 밀면 전문점. 쫄깃한 면발과 깊은 육수 맛이 일품입니다.",
            null,
            null,
            listOf("물밀면", "비빔밀면", "왕만두", "온밀면(겨울메뉴)"),
            listOf("여름철 별미", "해장 추천", "빠른 식사", "포장 가능")
        )
    )

    val accommodationList = listOf(
        Info(
            1,
            "https://www.kr-gyeongju.com/data/Photos/OriginalPhoto/11196/1119635/1119635523/photo-gyeongju-1.JPEG",
            "한옥스테이",
            "황남관 한옥 체험관",
            "1박 150,000원 ~ (객실 타입별 상이)",
            "경상북도 경주시 황남동 123-4",
            "전통 한옥의 아름다움을 느낄 수 있는 고즈넉한 숙소입니다. 황리단길 인접하여 관광에 용이합니다.",
            "15:00", // 체크인 시간
            "11:00", // 체크아웃 시간
            listOf("온돌방", "전통 다도 체험", "한복 대여", "조식 제공(유료)"),
            listOf("황리단길 인접", "고즈넉한 분위기", "무료 Wi-Fi", "주차 가능")
        ),
        Info(
            2,
            "https://hiltongj.cdn2.cafe24.com/renew/opengraph/hilton-social-img.jpg",
            "호텔",
            "경주 힐튼 호텔",
            "1박 200,000원 ~ (디럭스룸 기준)",
            "경상북도 경주시 보문로 484-7",
            "보문관광단지에 위치한 5성급 호텔로, 다양한 부대시설과 최상의 서비스를 제공합니다.",
            "15:00",
            "12:00",
            listOf("실내/외 수영장", "피트니스 센터", "사우나", "레스토랑 및 바"),
            listOf("보문호수 전망", "가족 친화적", "비즈니스 센터", "컨시어지 서비스")
        ),
        Info(
            3,
            "https://starmaru.net/wysiwyg/PEG/se2_16224139022006.jpg",
            "펜션",
            "별빛마루 펜션",
            "1박 120,000원 ~ (2인실 기준)",
            "경상북도 경주시 불국로 567-8",
            "불국사 근처 조용한 곳에 위치한 펜션입니다. 개별 바비큐 시설과 아름다운 정원을 갖추고 있습니다.",
            "15:00",
            "11:00",
            listOf("개별 바비큐장", "정원", "족욕 시설", "커플룸/가족룸"),
            listOf("불국사 인접", "조용한 휴식", "자연 친화적", "무료 주차")
        ),
        Info(
            4,
            "https://mblogthumb-phinf.pstatic.net/MjAyMzA2MDlfMTk4/MDAxNjg2Mjk4MzQ2MzUy.blQ2suIJH199Dsh0Lcn1u0-fa66_r76hG-CzlCRxelgg.wypEM6hnBSglEyiwLM91cecUAXGJ2B5wXMt-l-AX3SQg.JPEG.ksw666666/IMG_7518.JPG?type=w800",
            "게스트하우스",
            "여행자의 집",
            "도미토리 1박 30,000원~, 2인실 70,000원~",
            "경상북도 경주시 봉황로 9-1",
            "경주 시내 중심가에 위치한 깔끔하고 모던한 게스트하우스입니다. 여행자들 간의 교류가 활발합니다.",
            "16:00",
            "11:00",
            listOf("공용 주방", "라운지", "세탁 시설", "여행 정보 제공"),
            listOf("시내 접근성 좋음", "가성비", "개인 사물함", "친절한 스탭")
        ),
        Info(
            5,
            "https://yaimg.yanolja.com/v5/2022/09/02/16/1280/63122d5ddc8598.98939695.jpg",
            "부티크 호텔",
            "라궁 (신라밀레니엄파크 내)",
            "1박 300,000원 ~",
            "경상북도 경주시 엑스포로 55-12",
            "신라밀레니엄파크 내에 위치한 고급 전통 한옥 호텔로, 프라이빗한 휴식을 즐길 수 있습니다.",
            "15:00",
            "11:00",
            listOf("개별 노천탕", "한정식 레스토랑", "전통 건축미", "프라이빗 정원"),
            listOf("특별한 경험", "럭셔리 휴양", "조용함", "신라밀레니엄파크 연계")
        ),
    )
}