package com.example.mjapp.ui.screen.game.elsword

import androidx.compose.ui.graphics.Color
import com.example.mjapp.R

enum class ElswordCharacters(
    val characterName: String,
    val color: Color,
    val sdImage: Int,
    val jobImage: List<Int>,
    val jobName: List<String>
) {
    Elsword(
        "엘소드",
        Color(0xFFBA333D),
        R.drawable.img_elsword_sd,
        listOf(
            R.drawable.img_elsword_sd_1,
            R.drawable.img_elsword_sd_2,
            R.drawable.img_elsword_sd_3,
            R.drawable.img_elsword_sd_4
        ),
        listOf(
            "나이트 엠퍼러",
            "룬 마스터",
            "임모탈",
            "제네시스"
        )
    ),
    Aisha(
        "아이샤",
        Color(0xFF7E30A0),
        R.drawable.img_aisha_sd,
        listOf(
            R.drawable.img_aisha_sd_1,
            R.drawable.img_aisha_sd_2,
            R.drawable.img_aisha_sd_3,
            R.drawable.img_aisha_sd_4
        ),
        listOf(
            "에테르 세이지",
            "오즈 소서러",
            "메타모르피",
            "로드 아조트"
        )
    ),
    Rena(
        "레나",
        Color(0xFF91B23B),
        R.drawable.img_rena_sd,
        listOf(
            R.drawable.img_rena_sd_1,
            R.drawable.img_rena_sd_2,
            R.drawable.img_rena_sd_3,
            R.drawable.img_rena_sd_4
        ),
        listOf(
            "아네모스",
            "데이브레이커",
            "트와일라잇",
            "프로피티스"
        )
    ),
    Raven(
        "레이븐",
        Color(0xFF666B70),
        R.drawable.img_raven_sd,
        listOf(
            R.drawable.img_raven_sd_1,
            R.drawable.img_raven_sd_2,
            R.drawable.img_raven_sd_3,
            R.drawable.img_raven_sd_4
        ),
        listOf(
            "퓨리어스 블레이드",
            "레이지 하츠",
            "노바 임퍼레이터",
            "레버넌트"
        )
    ),
    Eve(
        "이브",
        Color(0xFFDC91A2),
        R.drawable.img_eve_sd,
        listOf(
            R.drawable.img_eve_sd_1,
            R.drawable.img_eve_sd_2,
            R.drawable.img_eve_sd_3,
            R.drawable.img_eve_sd_4
        ),
        listOf(
            "코드:얼티메이트",
            "코드:에센시아",
            "코드:사리엘",
            "코드:안티테제"
        )
    ),
    Chung(
        "청",
        Color(0xFF58BED2),
        R.drawable.img_chung_sd,
        listOf(
            R.drawable.img_chung_sd_1,
            R.drawable.img_chung_sd_2,
            R.drawable.img_chung_sd_3,
            R.drawable.img_chung_sd_4
        ),
        listOf(
            "코멧 크루세이더",
            "페이탈 팬텀",
            "센츄리온",
            "디우스 아에르"
        )
    ),
    Ara(
        "아라",
        Color(0xFFEB831D),
        R.drawable.img_ara_sd,
        listOf(
            R.drawable.img_ara_sd_1,
            R.drawable.img_ara_sd_2,
            R.drawable.img_ara_sd_3,
            R.drawable.img_ara_sd_4
        ),
        listOf(
            "비천",
            "범황",
            "대라",
            "일천"
        )
    ),
    Elesis(
        "엘레시스",
        Color(0xFFA72F40),
        R.drawable.img_elesis_sd,
        listOf(
            R.drawable.img_elesis_sd_1,
            R.drawable.img_elesis_sd_2,
            R.drawable.img_elesis_sd_3,
            R.drawable.img_elesis_sd_4
        ),
        listOf(
            "엠파이어 소드",
            "플레임 로드",
            "블러디 퀸",
            "아드레스티아"
        )
    ),
    Add(
        "애드",
        Color(0xFF6F4AD0),
        R.drawable.img_add_sd,
        listOf(
            R.drawable.img_add_sd_1,
            R.drawable.img_add_sd_2,
            R.drawable.img_add_sd_3,
            R.drawable.img_add_sd_4
        ),
        listOf(
            "둠 브링어",
            "도미네이터",
            "매드 패러독스",
            "오버마인드"
        )
    ),
    Luciel(
        "루시엘",
        Color(0xFF2567C7),
        R.drawable.img_luciel_sd,
        listOf(
            R.drawable.img_luciel_sd_1,
            R.drawable.img_luciel_sd_2,
            R.drawable.img_luciel_sd_3,
            R.drawable.img_luciel_sd_4
        ),
        listOf(
            "카타스트로피",
            "이노센트",
            "디앙겔리온",
            "데메르시오"
        )
    ),
    Rose(
        "로제",
        Color(0xFFF0C429),
        R.drawable.img_rose_sd,
        listOf(
            R.drawable.img_rose_sd_1,
            R.drawable.img_rose_sd_2,
            R.drawable.img_rose_sd_3,
            R.drawable.img_rose_sd_4
        ),
        listOf(
            "템페스트 버스터",
            "블랙 매서커",
            "미네르바",
            "프라임 오퍼레이터"
        )
    ),
    Ain(
        "아인",
        Color(0xFF56C7A9),
        R.drawable.img_ain_sd,
        listOf(
            R.drawable.img_ain_sd_1,
            R.drawable.img_ain_sd_2,
            R.drawable.img_ain_sd_3,
            R.drawable.img_ain_sd_4
        ),
        listOf(
            "리히터",
            "블루헨",
            "헤르셔",
            "비고트"
        )
    ),
    Laby(
        "라비",
        Color(0xFFDE4D78),
        R.drawable.img_laby_sd,
        listOf(
            R.drawable.img_laby_sd_1,
            R.drawable.img_laby_sd_2,
            R.drawable.img_laby_sd_3,
            R.drawable.img_laby_sd_4
        ),
        listOf(
            "이터니티 위너",
            "라디언트 소울",
            "니샤 라비린스",
            "트윈즈 피카로"
        )
    ),
    Noah(
        "노아",
        Color(0xFF424AB4),
        R.drawable.img_noah_sd,
        listOf(
            R.drawable.img_noah_sd_1,
            R.drawable.img_noah_sd_2,
            R.drawable.img_noah_sd_3,
            R.drawable.img_noah_sd_4
        ),
        listOf(
            "리버레이터",
            "셀레스티아",
            "닉스 피에타",
            "모르페우스"
        )
    );

    companion object {
        fun getCharacterColor(characterName: String) =
            ElswordCharacters.values().find { it.characterName == characterName }?.color ?: Elsword.color

    }
}