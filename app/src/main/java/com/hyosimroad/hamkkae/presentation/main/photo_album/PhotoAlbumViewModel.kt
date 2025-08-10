package com.hyosimroad.hamkkae.presentation.main.photo_album

import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.domain.model.Album
import com.hyosimroad.hamkkae.domain.model.Album.Photo

class PhotoAlbumViewModel : ViewModel() {
    val albumList = listOf(
        Album(
            1, "경주 역사문화 코스", "2025.06.14", "2025.06.17", "경주시", listOf(
                Photo(
                    1,
                    "https://www.gyeongju.go.kr/design/tour2019/img/sub/themaImg3.jpg",
                    "첨성대",
                    "밤하늘의 별을 관측하던 신라시대 건축물",
                    "2025.06.14 19:00",
                    listOf("야경", "별", "신라", "역사유적")
                ),
                Photo(
                    2,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMq7bQ-R0hiB4yRlqy90bmRwkA_maZnDX3_k0Xq7FYZ1jjxO5s44ivXRDBsfvCG4bYp_Q&usqp=CAU",
                    "불국사",
                    "신라 경덕왕 때 창건된 아름다운 사찰",
                    "2025.06.15 10:30",
                    listOf("사찰", "문화재", "신라", "단풍")
                ),
                Photo(
                    3,
                    "https://mblogthumb-phinf.pstatic.net/MjAyNDA5MjVfMTI0/MDAxNzI3MjE5NTk5MzMw.4O1M4YHD_HhT9A7H6dmgmnwmQjsxmZc9-aad_IbfBGog.sAIRyuBt2ze_X7euNNQas3KlHKrqtGWkKQCZmUZc0ikg.JPEG/DSC_4085.jpg?type=w800",
                    "석굴암",
                    "통일신라시대의 대표적인 석굴사원",
                    "2025.06.15 14:00",
                    listOf("국보", "불교미술", "조각", "일출")
                ),
                Photo(
                    4,
                    "https://myvenus.co.kr/files/attach/images/2040/672/424/fc318bed7159b3b00772faeab9e9ad2c.JPG",
                    "동궁과 월지",
                    "신라 왕궁의 별궁터, 야경이 아름다운 곳",
                    "2025.06.16 20:00",
                    listOf("야경명소", "궁궐", "연못", "신라역사")
                ),
                Photo(
                    5,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTU1gMflmj9fJzdTBwXKar33BAZ87TNuxr8JA&s",
                    "황리단길",
                    "전통과 현대가 어우러진 경주의 핫플레이스",
                    "2025.06.17 11:00",
                    listOf("한옥", "카페거리", "맛집탐방", "젊음의거리")
                )
            ), listOf("감동", "경건함", "가족애", "전통미", "평온", "역사")
        ),
        Album(
            2, "부산 해변 드라이브", "2025.07.01", "2025.07.03", "부산시", listOf(
                Photo(
                    6,
                    "https://www.saha.go.kr/tour/images/sub/img_01_01_01.jpg",
                    "다대포 해수욕장",
                    "넓은 백사장과 아름다운 일몰을 자랑하는 해변",
                    "2025.07.01 18:30",
                    listOf("일몰명소", "해수욕", "갯벌체험", "산책")
                ),
                Photo(
                    7,
                    "https://visitbusan.net/uploadImgs/files/cntnts/20221215173025725_oen",
                    "해운대 해수욕장",
                    "부산을 대표하는 유명한 해수욕장",
                    "2025.07.02 15:00",
                    listOf("백사장", "여름휴가", "바다수영", "축제")
                ),
                Photo(
                    8,
                    "https://cdn.epnc.co.kr/news/photo/202001/93682_85075_3859.jpg",
                    "광안대교",
                    "밤바다를 가로지르는 아름다운 다리, 야경 명소",
                    "2025.07.02 21:00",
                    listOf("야경드라이브", "불꽃축제", "바다전망", "랜드마크")
                )
            ), listOf("힐링", "바다", "낭만", "드라이브", "해변", "휴식", "야경", "맛집")
        ),
        Album(
            3, "제주도 한라산 등반", "2025.08.10", "2025.08.12", "제주시", listOf(
                Photo(
                    9,
                    "https://san.chosun.com/news/photo/201112/6500_22519_2612.jpg",
                    "한라산 백록담",
                    "한라산 정상에 위치한 화산호",
                    "2025.08.11 12:00",
                    listOf("정상", "화산", "등산", "자연경관")
                ),
                Photo(
                    10,
                    "https://cdn.mhns.co.kr/news/photo/202201/520746_630720_356.jpg",
                    "한라산 관음사 코스",
                    "한라산 등반 코스 중 하나, 아름다운 계곡 풍경",
                    "2025.08.11 09:00",
                    listOf("계곡", "숲길", "트레킹", "피톤치드")
                )
            ), listOf("도전", "자연", "성취감", "풍경")
        )
    )
}