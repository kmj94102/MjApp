package com.example.network.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Persona3CommunitySelect(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rank: Int,
    val arcana: String,
    val contents: String
)

@Entity
data class Persona3Quest (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val deadline: String,
    val condition: String,
    val contents: String,
    val guide: String,
    val reward: String,
    val isComplete: Boolean = false
)

fun createPersona3QuestList(): List<Persona3Quest> =
    listOf(
        Persona3Quest(
            title = "No.1 머슬 드링크를 마셔 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "머슬 드링크 1개를 엘리자베스에게 전달한다",
            guide = "몰로니안 몰 '푸른 수염 약국'에서 머슬 드링크를 1,000엔에 구매 후 전달",
            reward = "소울 드롭 x5"
        ),
        Persona3Quest(
            title = "No.2 인공섬 계획 문서를 입수하라",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 01을 가져온다",
            guide = "타르타로스 22F 보물상자 안에서 문서를 찾아 전달",
            reward = "10,000엔"
        ),
        Persona3Quest(
            title = "No.3 섀도 헌팅을 해 보자",
            deadline = "기한없음",
            condition = "",
            contents = "섀도를 누적 100체 토벌한다",
            guide = "전투 돌입 후 등장하는 섀도 개체 누적 토벌 수 100체를 토벌 후 보고",
            reward = "큐어 워터 x3"
        ),
        Persona3Quest(
            title = "No.4 트레저 헌팅을 해 보자",
            deadline = "기한없음",
            condition = "",
            contents = "보물 상자를 누적 50개 연다",
            guide = "일반 / 희귀 구분없이 총 50개를 열고 엘리자베스에게 보고- 50개의 보물 상자를 열면 트로피/도전과제 '서류 가방 너무 좋아' 획득 알림으로 확인 가능",
            reward = "스너프 소울 x2"
        ),
        Persona3Quest(
            title = "No.5 레벨 13 이상인 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 13 이상인 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "주인공 레벨 13 이상 달성 후 레벨 13이상의 페르소나를 창조 후 보고",
            reward = "부흐라 젬 x3"
        ),
        Persona3Quest(
            title = "No.6 코우하를 지닌 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "No.5 의뢰 완료 후 해금",
            contents = "코우하를 지닌 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "Lv. 10 합체가능한 아크엔젤이 코우하를 보유함[전차] 아라미타마 + [법황] 오모이카네 = [정의] 아크 엔젤[마술사] 잭 프로스트 + [여법황] 아프사라스 = [정의] 아크 엔젤[여법황] 아프사라스 + [황제] 포르네우스 = [정의] 아크 엔젤",
            reward = "맹공의 경문 x2"
        ),
        Persona3Quest(
            title = "No.7 쥬즈마루 츠네츠구를 가져와 줘",
            deadline = "기한없음",
            condition = "No.3 의뢰 완료 후 해금",
            contents = "쥬즈마루 츠네츠구를 엘리자베스에게 보여준다",
            guide = "타르타로스 36F 보스 처치 후 왼쪽 박명의 파편 x3 상자에서 획득 확인",
            reward = "마하코우하 스킬 카드"
        ),
        Persona3Quest(
            title = "No.8 점술의 효과를 시험해 봐",
            deadline = "기한없음",
            condition = "",
            contents = "희소종 출현율 상승의 \"행운 점술\"을 시험한다",
            guide = "클럽 에스카 페이드의 점술사\"엄마\"에게 행운 점술을 점친 후 타르타로스에서 희소종 토벌 후 보고",
            reward = "스피드 인센스Ⅰ x1"
        ),
        Persona3Quest(
            title = "No.9 다양한 주스를 마셔 보고 싶어",
            deadline = "기한없음",
            condition = "No.1 의뢰 완료 후 해금다",
            contents = "자판기의 주스를 12종류 모아서 엘리자베스에게 건넨",
            guide = "",
            reward = "메디아 x1"
        ),
        Persona3Quest(
            title = "No.10 소고기덮밥을 먹고 싶어",
            deadline = "기한없음",
            condition = "No.1 의뢰 완료 후 해금",
            contents = "우미우시 소고기덮밥 1개를 가져온다",
            guide = "기숙사 라운지 '공용컴퓨터'에서 우미우시 팬북 사용 후 포장 가능",
            reward = "남성 동복 세트"
        ),
        Persona3Quest(
            title = "No.11 많이 먹기 챌린지에 도전해 줘",
            deadline = "기한없음",
            condition = "No.9 의뢰 완료 후 해금",
            contents = "와일덕 버거의 많이 먹기 챌린지를 달성",
            guide = "대화 선택 [2번 / 1번 / 2번] 순으로 선택",
            reward = "박명의 파편 x3"
        ),
        Persona3Quest(
            title = "No.12 송진 가루를 가져와 줘",
            deadline = "6월 6일까지",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '타케바 유카리'와 대화 후 송진가루를 획득 후 보고",
            reward = "장난감 활"
        ),
        Persona3Quest(
            title = "No.13 휴대용 게임기를 가져와 줘",
            deadline = "6월 6일까지",
            condition = "No.12 의뢰 완료 후 해금",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '이오리 준폐이'와 대화 후 휴대용 게임기를 획득 후 보고",
            reward = "픽셀 베스트"
        ),
        Persona3Quest(
            title = "No.14 인공섬 계획 문서를 입수하라 2",
            deadline = "기한없음",
            condition = "No.2 의뢰 완료 후 해금",
            contents = "인공섬 계획 문서 02을 가져온다",
            guide = "타르타로스 43F 보물상자 안에서 문서를 찾아 전달",
            reward = "20,000엔"
        ),
        Persona3Quest(
            title = "No.15 섀도 헌팅을 해 보자 2",
            deadline = "기한없음",
            condition = "No.3 의뢰 완료 후 해금",
            contents = "섀도를 누적 200체 토벌한다",
            guide = "전투 돌입 후 등장하는 섀도 개체 누적 토벌 수 200체를 토벌 후 보고",
            reward = "대합 워터 x3"
        ),
        Persona3Quest(
            title = "No.16 트레저 헌팅을 해 보자 2",
            deadline = "기한없음",
            condition = "",
            contents = "보물 상자를 누적 100개 연다",
            guide = "",
            reward = "츄잉 소울 x2"
        ),
        Persona3Quest(
            title = "No.17 페르소나 작성 과제 1 : 황제 오베론",
            deadline = "기한없음",
            condition = "No.5 의뢰 완료 후 해금",
            contents = "마하지오를 지닌 황제 오베론을 소지한 상태로 엘리자베스에게 보고한다",
            guide = "오베른 합체 시 17레벨 달성하면 마하지오를 배움",
            reward = "여성 동복 세트"
        ),
        Persona3Quest(
            title = "No.18 꽃다발 선물을 받아 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "장미 꽃다발 1개를 가져온다",
            guide = "포트 아일랜드 역 앞 광자 꽃집 '라플라시아의 누님'에게 구매",
            reward = "여성 겨울 사복 세트"
        ),
        Persona3Quest(
            title = "No.19 잭 프로스트 인형이 필요해",
            deadline = "기한없음",
            condition = "No.18 의뢰 완료 후 해금",
            contents = "잭 프로스트 인형 3개를 가져온다",
            guide = "매주 금요일 폴로니안 몰 '게임 퍼레이드'앞 인형 뽑기에서 얻을 수 있음\n※ 뽑을 때까지 도전한다 (약 800 ~ 1,400엔 소모)",
            reward = "박명의 파편 x3"
        ),
        Persona3Quest(
            title = "No.20 극약을 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "극약 1개를 가져온다",
            guide = "1F 교무실 앞 복도 '양호실'에서 극약을 획득 후 전달 (용기 4 이상)",
            reward = "쇠 파이프"
        ),
        Persona3Quest(
            title = "No.21 인공섬 계획 문서를 입수하라 3",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 03을 가져온다",
            guide = "타르타로스 69F 보물상자 안에서 문서를 찾아 전달",
            reward = "30,000엔"
        ),
        Persona3Quest(
            title = "No.22 섀도 헌팅을 해 보자 3",
            deadline = "기한없음",
            condition = "No.15 의뢰 완료 후 해금",
            contents = "섀도를 누적 300체 토벌한다",
            guide = "전투 돌입 후 등장하는 섀도 개체 누적 토벌 수 300체를 토벌 후 보고",
            reward = "보옥 x3"
        ),
        Persona3Quest(
            title = "No.23 페르소나 합체를 해 보자",
            deadline = "기한없음",
            condition = "",
            contents = "페르소나 합체를 누적 20회 한다",
            guide = "",
            reward = "박명의 파편 x5"
        ),
        Persona3Quest(
            title = "No.24 레벨 23이상인 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 23 이상인 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "보상 - 과자 열쇠\n[페르소나 '킹 프로스트' 해금]"
        ),
        Persona3Quest(
            title = "No.25 페르소나 작성 과제 2 : 전차 미트라스",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 26 이상인 전차 미트라스를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "잭 오 랜턴 + 탐린 / 베리스 + 나가 = 미트라스LV. 25 스킬 아기라오 필수 기억",
            reward = "남성 겨울 사복 세트"
        ),
        Persona3Quest(
            title = "No.26 오니마루 쿠니츠나를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "오니마루 쿠니츠나를 엘리자베스에게 준다",
            guide = "타르타로스 54F 보스 처치 후 왼쪽 박명의 파편 x3 상자에서 획득 확인",
            reward = "크리티컬 UP x1"
        ),
        Persona3Quest(
            title = "No.27 삼각형 검을 가져와 줘",
            deadline = "7월 5일까지",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '키리조 미츠루'와 대화 후 경기용 에페를 획득 후 보고",
            reward = "용기의 스니커즈"
        ),
        Persona3Quest(
            title = "No.28 프로용이 아닌 프로테인을 가져와 줘",
            deadline = "7월 5일까지",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "",
            reward = "기숙사 라운지의 '사나다 아키히코'와 대화 후 아마프로틴를 획득 후 보고"
        ),
        Persona3Quest(
            title = "No.29 멋을 부리고 싶어",
            deadline = "7월 5일까지",
            condition = "",
            contents = "멋 낼 만한 아이템을 가져온다",
            guide = "클럽 에스카 페이드의 고급 상점 점원에게 10,000엔에 구매 (블랙 쿼츠가 있을 시 1만엔)",
            reward = "파워 인센스Ⅰ x5"
        ),
        Persona3Quest(
            title = "No.30 인공섬 계획 문서를 입수하라 4",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 05를 가져온다",
            guide = "",
            reward = "40,000엔"
        ),
        Persona3Quest(
            title = "No.31 섀도 헌팅을 해 보자 4",
            deadline = "기한없음",
            condition = "",
            contents = "섀도를 누적 450체 토벌한다",
            guide = "",
            reward = "카미무스비 워터"
        ),
        Persona3Quest(
            title = "No.32 트레저 헌팅을 해 보자 3",
            deadline = "기한없음",
            condition = "No.23 의뢰 완료 후 해금",
            contents = "페르소나 합체를 누적 35회 한다",
            guide = "",
            reward = "박명의 파편 x5"
        ),
        Persona3Quest(
            title = "No.33 페르소나 합체를 해 보자 2",
            deadline = "기한없음",
            condition = "No.23 의뢰 완료 후 해금",
            contents = "페르소나 합체를 누적 35회 한다",
            guide = "",
            reward = "박명의 파편 x5"
        ),
        Persona3Quest(
            title = "No.34 밀리언 슛을 지닌 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "",
            contents = "밀리언 슛을 지닌 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "피지컬 미러 x3"
        ),
        Persona3Quest(
            title = "No.35 페르소나 작성 과제 3: 은둔자 모스맨",
            deadline = "기한없음",
            condition = "",
            contents = "아기라오를 지닌 은둔자 모스맨을 소지한 상태로 엘리자베스에게 보고한다",
            guide = "미트라스 + 현무 = 모스맨 / 미트라스 LV25 아기라오 계승",
            reward = "미츠루 메이드복"
        ),
        Persona3Quest(
            title = "No.36 희소종을 쓰러뜨려라 1",
            deadline = "기한없음",
            condition = "",
            contents = "제3구역 야바자의 희소종을 쓰러뜨리고 '무골의 금화' 1개를 가져온다",
            guide = "",
            reward = " 오닉스 x7"
        ),
        Persona3Quest(
            title = "No.37 모나드 통로를 답파하라",
            deadline = "기한없음",
            condition = "",
            contents = "제3구역 야바자 91층의 모나드 통로에 숨은 섀도를 모두 토벌한다",
            guide = "",
            reward = "검은 검 x1"
        ),
        Persona3Quest(
            title = "No.38 차가운 붕어빵을 먹고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "차가운 흰 붕어빵 1개를 가져온다",
            guide = "",
            reward = "무의 방포 x1"
        ),
        Persona3Quest(
            title = "No.39 월광관 고등학교에 관련된 음악을 듣고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "월광관 고등학교에 관련된 CD를 가져온다",
            guide = "7/9 이후 의뢰를 받은 후 교실 앞 방송실에서 '월광가 CD' 획득 후 보고",
            reward = "여성 하복 세트"
        ),
        Persona3Quest(
            title = "No.40 슈퍼 안전화를 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "슈퍼 안전화 1개를 가져온다",
            guide = "7/12(일) 시가 넷 타나카 TV 홈쇼핑에서 구매 (9,800엔)",
            reward = "박명의 파편 x3"
        ),
        Persona3Quest(
            title = "No.41 정체 모를 인물의 사인을 받아 와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "시가 넷의 타나카 사장에게 사인을 받아 온다",
            guide = "악마 커뮤니티를 오픈 시 받을 수 있다",
            reward = "무의 칼날 x1"
        ),
        Persona3Quest(
            title = "No.42 고양이에게 먹이를 주고 와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "고양이에게 먹이를 줘서 건강하게 만든다",
            guide = "역 앞 광자 변두리의 고양이에게 '길고양이 통조림'을 4일동안 주면 달성",
            reward = "남성 여름 사복 세트"
        ),
        Persona3Quest(
            title = "No.43 오랑우탄 나무를 가져와 줘",
            deadline = "8월 4일까지",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '야마기시 후카'와 대화 후 성성목을 획득 후 보고",
            reward = "잭 장갑"
        ),
        Persona3Quest(
            title = "No.44 바다를 느끼고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "바다에서 발견한 물건을 가져온다",
            guide = "7/20 ~ 21 바다에서 구할 수 있는 물건 4개 중 1개 전달",
            reward = "애미시스트 x5"
        ),
        Persona3Quest(
            title = "No.45 인공섬 계획 문서를 입수라아 5",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 05를 가져온다",
            guide = "타르타로스 144F 보물상자 안에서 문서를 찾아 전달",
            reward = "50,000엔"
        ),
        Persona3Quest(
            title = "No.46 섀도 헌팅을 해보자 5",
            deadline = "기한없음",
            condition = "No.31 의뢰 완료 후 해금",
            contents = "섀도를 누적 600체 토벌한다",
            guide = "",
            reward = "보옥륜 x2"
        ),
        Persona3Quest(
            title = "No.47 트레저 헌팅을 해 보자 4",
            deadline = "기한없음",
            condition = "",
            contents = "보물 상자를 누적 200개를 연다",
            guide = "",
            reward = "금달걀 x2"
        ),
        Persona3Quest(
            title = "No.48 페르소나 합체를 해보자 3",
            deadline = "기한없음",
            condition = "",
            contents = "페르소나 합체를 누적 50회 한다",
            guide = "",
            reward = "박명의 파편 x5"
        ),
        Persona3Quest(
            title = "No.49 레벨 38이상인 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 38이상인 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "보상 - 끈이 끊어진 인형[페르소나 '네비로스' 해금]"
        ),
        Persona3Quest(
            title = "No.50 임금님과 나를 발현하라",
            deadline = "기한없음",
            condition = "",
            contents = "믹스 레이드 \"임금님과 나\"를 발현한다",
            guide = "",
            reward = "가드 인센스Ⅱ x1"
        ),
        Persona3Quest(
            title = "No.51 오오덴타 미츠요를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "오오덴타 미츠요를 엘리자베스에게 보여준다",
            guide = "",
            reward = "멀티 부스터 x1"
        ),
        Persona3Quest(
            title = "No.52 엄마의 손맛을 맛보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "기숙사에서 동료와 만든 요리를 가져온다",
            guide = "",
            reward = "식칼 전설 x1"
        ),
        Persona3Quest(
            title = "No.53 신비한 감자를 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "기른 감자를 가져온다",
            guide = "",
            reward = "에르고타이트 파편 x1"
        ),
        Persona3Quest(
            title = "No.54 백일기도를 시험해 봐",
            deadline = "기한없음",
            condition = "",
            contents = "기도의 성과를 가져온다",
            guide = "나가나키 신사에서 3일간 참배 시 '500엔 지폐' 획득 후 보고\n※ 돈을 넣지 않는 참배 / 시간이 지나지 않음",
            reward = "라임 세퍼릿"
        ),
        Persona3Quest(
            title = "No.55 인연의 결정을 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "커뮤 랭크 MAx달성 시에 받은 아이템을 가져온다",
            guide = "",
            reward = "???"
        ),
        Persona3Quest(
            title = "No.56 내 이름이 붙은 음료를 찾아 줘",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스라는 이름이 붙은 음료를 가져온다",
            guide = "8/8 이후 매력 5이상일때 '역 앞 변두리'의 '케세라세라'로 들어가면 '퀸 엘리자베스'를 획득 후 보고",
            reward = "AS 범용 머티리얼 x1"
        ),
        Persona3Quest(
            title = "No.57 녹즙을 마셔 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "궁극의 푸른 수염 녹즙을 1개를 가져온다",
            guide = "1. 의뢰 수락 후 '푸른 수염 약국'에서 녹즙 키워드로 '오래된 약연' 키워드 확인\n2. 골동품상 '마요이당'에서 토파즈 x2, 튀르쿠아즈 x1로 오래된 약연과 아이템 교환\n3. '푸른 수염 약국'에서 '오래된 약연'과 '궁극의 푸른 수염 녹즙'을 교환 후 보고",
            reward = "박명의 파편 x8"
        ),
        Persona3Quest(
            title = "No.58 짚대 장자가 돼 보고 싶어",
            deadline = "8월 31일까지",
            condition = "",
            contents = "물물 교환의 결과물을 가져온다",
            guide = "역 앞 광장 변두리 > 포트 아일랜드 역 앞 광장 > 이와토다이 역 앞 상점가 순으로 진행",
            reward = "튀르쿠아즈"
        ),
        Persona3Quest(
            title = "No.59 인공섬 계획 문서를 입수하라 6",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 06을 가져온다",
            guide = "",
            reward = "70,000엔"
        ),
        Persona3Quest(
            title = "No.60 섀도 헌팅을 해 보자 6",
            deadline = "기한없음",
            condition = "",
            contents = "섀도를 누적 800체 토벌한다",
            guide = "전투 돌입 후 등장하는 섀도 개체 누적 토벌 수 800체를 토벌 후 보고",
            reward = "소마 x1"
        ),
        Persona3Quest(
            title = "No.61 레벨 46 이상인 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 46 이상인 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "연약의 경문 x2"
        ),
        Persona3Quest(
            title = "No.62 페르소나 작성 과제 4: 연인 티타니아",
            deadline = "기한없음",
            condition = "",
            contents = "마하타루카쟈를 지닌 연인 티타니아를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "Ex1) 쿠훌린 + 다키니",
            reward = "남성 하복 세트"
        ),
        Persona3Quest(
            title = "No.63 페르소나 작성 과제 5: 마술사 랑다",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 54 이상인 마술사 랑다를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "여성 여름 사복 세트"
        ),
        Persona3Quest(
            title = "No.64 희소종을 쓰러뜨려라 2",
            deadline = "기한없음",
            condition = "",
            contents = "제4구역 트이아의 희소종을 쓰러뜨리고 호사의 금화 1개를 가져온다",
            guide = "",
            reward = "토파즈 x7"
        ),
        Persona3Quest(
            title = "No.65 오테기네를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "오테기네를 엘리자베스에게 보여준다",
            guide = "",
            reward = "무의 상원석 x1"
        ),
        Persona3Quest(
            title = "No.66 기괴한 거대 인형을 가져와 줘",
            deadline = "기한없음",
            condition = "No.39 의뢰 완료 후 해금",
            contents = "교재로 쓰이는 인형을 가져온다",
            guide = "9/10 이후 의뢰를 받은 후 1F 화확 실험실에서 '인체모형'을 획득 후 보고",
            reward = "무의 상인 x1"
        ),
        Persona3Quest(
            title = "No.67 뭔지 모를 예쁜 조각을 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "공작이 그려진 조각을 가져온다",
            guide = "9/10 이후 용기 5이상일때 '역 앞 변두리'의 '마작장 붉은 매'로 들어가면 '마작 패'를 획득 후 보고\n※ 대화 선택지 임의 선택 결과는 동일",
            reward = "바닥 솔"
        ),
        Persona3Quest(
            title = "No.68 과도를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "",
            reward = "버스 안내판"
        ),
        Persona3Quest(
            title = "No.69 오일을 가져와줘",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "",
            reward = "로켓 펀치"
        ),
        Persona3Quest(
            title = "No.70 인공섬 계획 문서를 입수하라 7",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 07을 가져온다",
            guide = "",
            reward = "90,000엔"
        ),
        Persona3Quest(
            title = "No.71 페르소나 작성 과제 6: 힘 지크프리트",
            deadline = "기한없음",
            condition = "",
            contents = "이 악물기를 지닌 힘 지크프리트를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "스카이 드레스"
        ),
        Persona3Quest(
            title = "No.72 페르소나 작성 과제 7: 법황 대승정",
            deadline = "기한없음",
            condition = "",
            contents = "치유 촉지 대를 지닌 법황 대승정을 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "블루 버뮤다"
        ),
        Persona3Quest(
            title = "No.73 미카즈키 무네치카를 가져와 줘",
            deadline = "기한없음",
            condition = "No.51 의뢰 완료 후 해금",
            contents = "미카즈키 무네치카를 엘리자베스에게 보여준다",
            guide = "",
            reward = "이 악물기 x1"
        ),
        Persona3Quest(
            title = "No.74 매력적인 초밥을 먹고 싶어",
            deadline = "기한없음",
            condition = "No.53 의뢰 완료, 10/6 이후",
            contents = "초밥을 가져온다",
            guide = "10/6 이후 나가나키 신사 '사당'에서 '유부초밥'을 획득 후 보고",
            reward = "에르고타이트 덩어리 x1"
        ),
        Persona3Quest(
            title = "No.75 전국 시대의 투구를 가져와 줘",
            deadline = "기한없음",
            condition = "No.66 의뢰 완료 후 해금\n",
            contents = "전국 시대의 투구를 가져온다",
            guide = "의뢰 수락 후 9일동안 1F 교무실에 입장하면 '나오에 카네츠구의 투구'을 획득 후 보고",
            reward = "박명의 파편 x7"
        ),
        Persona3Quest(
            title = "No.76 안경 닦이를 가져와 줘",
            deadline = "11월 1일까지",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '이쿠츠키 슈지'와 대화 후 안경 닦이를 획득 후 보고",
            reward = "가넷 x5"
        ),
        Persona3Quest(
            title = "No.77 폴로니안 몰을 돌아다니고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스와 함께 폴로니안 몰을 산책",
            guide = "의뢰 약 10개 이상 완료 시 수행 가능퀘스트를 진행해도 시간이 지나지 않습니다.",
            reward = "보상 - 작은 차이나 드레스\n[페르소나 '화백' 해금]"
        ),
        Persona3Quest(
            title = "No.78 이와토다이에 가 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스와 함께 이와토다이 주변을 산책한다",
            guide = "의뢰 20개 이상 완료 시 수행 가능\n퀘스트를 진행해도 시간이 지나지 않습니다",
            reward = "보상 - 고대어 책\n[페르소나 '토트' 해금]"
        ),
        Persona3Quest(
            title = "No.79 나가나키 신사에 가 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스와 함께 나가나키 신사를 산책한다",
            guide = "의뢰 40개 이상 완료 시 수행 가능\n퀘스트를 진행해도 시간이 지나지 않습니다.",
            reward = "초활천의 허리띠"
        ),
        Persona3Quest(
            title = "No.80 월광관 고등학교에 가 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스에게 월광관 고등학교를 안내한다",
            guide = "의뢰 60개 이상 완료 시 수행 가능\n퀘스트를 진행해도 시간이 지나지 않습니다.",
            reward = "도사 배지"
        ),
        Persona3Quest(
            title = "No.81 당신의 방에 가 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스를 내 방에 초대한다",
            guide = "의뢰 80개 이상 완료 시 수행 가능\n퀘스트를 진행해도 시간이 지나지 않습니다.",
            reward = "보상 - 마왕의 뿔\n[페르소나 '루시퍼' 해금]"
        ),
        Persona3Quest(
            title = "No.82 인공섬 계획 문서를 입수하라 최종",
            deadline = "기한없음",
            condition = "",
            contents = "인공섬 계획 문서 08을 가져온다",
            guide = "",
            reward = "120,000엔"
        ),
        Persona3Quest(
            title = "No.83 경과 관찰 보고서를 입수하라",
            deadline = "기한없음",
            condition = "",
            contents = "1기 경과 관찰 보고서를 가져온다",
            guide = "",
            reward = "150,000엔"
        ),
        Persona3Quest(
            title = "No.84 장대비 베기를 지닌 페르소나를 창조하라",
            deadline = "기한없음",
            condition = "",
            contents = "장대비 베기를 지닌 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "순간 증강의 경문 x3"
        ),
        Persona3Quest(
            title = "No.85 마하라쿠카 오토를 지닌 페르소나를 창조하라\t",
            deadline = "기한없음",
            condition = "",
            contents = "마하라쿠카 오토를 지닌 페르소나를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "장절약체의 경문 x3"
        ),
        Persona3Quest(
            title = "No.86 페르소나 작성 과제 8: 사신 앨리스",
            deadline = "기한없음",
            condition = "",
            contents = "사신 앨리스를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "아이기스 메이드복"
        ),
        Persona3Quest(
            title = "No.87 페르소나 작성 과제 9: 광대 로키",
            deadline = "기한없음",
            condition = "",
            contents = "레벨 75 이상인 광대 로키를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "보상 - 마사카두스\n[페르소나 '마사카도' 해금]"
        ),
        Persona3Quest(
            title = "No.88 거대 희소종을 쓰러뜨려라",
            deadline = "기한없음",
            condition = "",
            contents = "제5구역 하라바의 거대 희소종을 쓰러뜨리고 골드 메달 1개를 가져온다",
            guide = "",
            reward = "승리의 숨결 x1"
        ),
        Persona3Quest(
            title = "No.89 라이쿠니미츠를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "라이쿠니미츠를 엘리자베스에게 보여준다",
            guide = "",
            reward = "무의 최고 원석 x1"
        ),
        Persona3Quest(
            title = "No.90 도지기리 야스츠나를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "도지기리 야스츠나를 엘리자베스에게 보여준다",
            guide = "",
            reward = "AS 강화 머티리얼 x1"
        ),
        Persona3Quest(
            title = "No.91 톤보키리를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "톤보키리를 엘리자베스에게 보여준다",
            guide = "보상 - 무의 흑원형 x2",
            reward = "No.73 의뢰 완료 후 해금"
        ),
        Persona3Quest(
            title = "No.92 화장실을 청소하고 와 줘",
            deadline = "기한없음",
            condition = "No.67 의뢰 완료 후 해금",
            contents = "청소 도구를 가져가서 화장실을 청소한다",
            guide = "No.67 보상 바닥 솔을 소지한 상태에서 포트 아일랜드 역 화장실 청소 후 보고\n※아마다 켄이 장착 시 미 소지상태로 인식 됨",
            reward = "유카리 메이드복"
        ),
        Persona3Quest(
            title = "No.93 꽃에 물을 주고 와 줘",
            deadline = "기한없음",
            condition = "No.75 의뢰 완료 후 해금",
            contents = "학교 화단에 물을 준다",
            guide = "학교 옥상에 가운데 화분에 물을 준 후 보고",
            reward = "후카 메이드복"
        ),
        Persona3Quest(
            title = "No.94 로마의 음식을 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '코로마루'와 대화 후 고급 개 사료를 획득하여 전달",
            reward = "뼈"
        ),
        Persona3Quest(
            title = "No.95 페더맨 R 인형을 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "엘리자베스가 원하는 물건을 가져온다",
            guide = "기숙사 라운지의 '아마다 켄'와 대화 후 페더맨 R 인형을 획득하여 전달",
            reward = "신의 제물"
        ),
        Persona3Quest(
            title = "No.96 어묵 주스를 마셔 보고 싶어",
            deadline = "기한없음",
            condition = "",
            contents = "어묵 주스를 가져온다",
            guide = "11/17 교토 수학여행 중 숙소 2F 자판기에서 음료수 모두 구매 후 11/20일 이후\n월광관 고등학교 연결복도의 여학생에게 교토 음료수를 보여주고 5천엔에 어묵주스 구매",
            reward = "월광관 고등학교 동복"
        ),
        Persona3Quest(
            title = "No.97 크리스마스 선물을 받아 와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "산타에게 크리스마스 선물을 받아 온다",
            guide = "50F 실종자 보상 감사의편지 필수 / 이와토다이 역 앞 상점 2F '색다른 남성'과 대화 후\n산타 모자를 얻어 엘리자베스에게 보고 시 크리스마스 선물 '건가의 향합' 획득",
            reward = ""
        ),
        Persona3Quest(
            title = "No.98 페르소나 작성 과제 10: 탑 마사카도",
            deadline = "기한없음",
            condition = "No.87 의뢰 완료 후 해금",
            contents = "차지를 지닌 탑 마사카도를 소지한 상태로 엘리자베스에게 보고한다",
            guide = "",
            reward = "무의 백원형"
        ),
        Persona3Quest(
            title = "No.99 대심연의 그림자를 토벌하라",
            deadline = "기한없음",
            condition = "",
            contents = "제6 구역 아마다 255층의 모나드 통로에 숨은 \"대심연의 그림자\"를 토벌한다",
            guide = "",
            reward = "어두운 달의 유해"
        ),
        Persona3Quest(
            title = "No.100 피에 젖은 단추를 가져와 줘",
            deadline = "기한없음",
            condition = "",
            contents = "피에 젖은 단추를 가져온다",
            guide = "거둬드린 자를 쓰리트리면 획득",
            reward = "완전신주"
        ),
        Persona3Quest(
            title = "No.101 최강의 존재를 쓰러뜨려라",
            deadline = "기한없음",
            condition = "",
            contents = "최강의 적을 쓰러뜨린다",
            guide = "255F 모나드문 가장 안쪽의 문으로 들어가서 '그분'과 전투 후 승리\n※ 진행하기 전에 - 입장 전 세이브 데이터 저장\n최강의 적을 처치 시 '백금 세공 책갈피' 획득",
            reward = "전능의 진구"
        ),
    )

fun createPersona3CommunitySelectList(): List<Persona3CommunitySelect> =
    listOf(
        // 마법사
        Persona3CommunitySelect(
            rank = 2,
            arcana = "마법사",
            contents = "그건 비밀\n연상이 최고야"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "마법사",
            contents = "인생이?\n힘내"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "마법사",
            contents = "임의 선택\n힘내"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "마법사",
            contents = "그렇네..."
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "마법사",
            contents = "임의 선택\n이미 정했어"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "마법사",
            contents = "무슨 일 있었어?\n결혼 정보지\n축하해"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "마법사",
            contents = "고민 있어?\n둘이서 상의해"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "마법사",
            contents = "한마디하고 올게"
        ),

        // 여법황
        Persona3CommunitySelect(
            rank = 2,
            arcana = "여법황",
            contents = "상관없어\n임의 선택\n딱히 상관없어"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "여법황",
            contents = "착착...\n급할 거 없어\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "여법황",
            contents = "임의 선택\n후카는 노력가야\n그렇지 않아"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "여법황",
            contents = "노력했구나\n도움이 됐다니 다행이네\n또 만들어 줘"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "여법황",
            contents = "딱히 상관없어\n바로 그 마음가짐이야"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "여법황",
            contents = "임의 선택\n임의 선택\n너무하네\n정말 그것뿐이야?"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "여법황",
            contents = "임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "여법황",
            contents = "임의 선택\n임의 선택\n후카를 좋아해\n임의 선택"
        ),

        // 여황제
        Persona3CommunitySelect(
            rank = 2,
            arcana = "여황제",
            contents = "임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "여황제",
            contents = "먹어볼래?\n기뻐?\n임의 선택\n잊을게"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "여황제",
            contents = "무슨 일 있었어?\n사랑의 결실\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "여황제",
            contents = "즐거웠다니 다행이야\n오토바이?\n언젠가 함께 여행하고 싶어"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "여황제",
            contents = "찾는 책이라도?\n임의 선택\n처음 듣는데\n내가 어떻게든 할게"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "여황제",
            contents = "알았어"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "여황제",
            contents = "임의 선택\n임의 선택\n미츠루의 자유야\n용서 못 해\n시키는 대로 할 거야?"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "여황제",
            contents = "기뻤어\n임의 선택\n나도 미츠루를 좋아해\n임의 선택"
        ),

        // 황제
        Persona3CommunitySelect(
            rank = 2,
            arcana = "황제",
            contents = "한심해"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "황제",
            contents = "임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "황제",
            contents = "임의 선택\n열심히 하네"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "황제",
            contents = "나쁜 사람들이네"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "황제",
            contents = "임의 선택\n방금 왔는데..."
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "황제",
            contents = "과한 처분이야"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "황제",
            contents = "임의 선택\n난 범인이 아니야"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "황제",
            contents = "자책하지 마"
        ),

        // 법황
        Persona3CommunitySelect(
            rank = 2,
            arcana = "법황",
            contents = "(본인 이름)입니다\n고마워요\n임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "법황",
            contents = "뭐 찾으세요?\n도와드릴게요\n임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "법황",
            contents = "임의 선택\n임의 선택\n걱정이네요"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "법황",
            contents = "나 아무렇지도 않아요\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "법황",
            contents = "싸우지 마세요\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "법황",
            contents = "대체 무슨 일이에요?\n그거 다행이네요\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "법황",
            contents = "임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "법황",
            contents = "임의 선택 all"
        ),

        // 연인
        Persona3CommunitySelect(
            rank = 2,
            arcana = "연인",
            contents = "귀여운 핑크\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "연인",
            contents = "임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "연인",
            contents = "괜찮아?"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "연인",
            contents = "임의 선택\n임의 선택\n임의 선택\n남자 친구다\n미안해"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "연인",
            contents = "그 정도쯤이야\n신경 안 써"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "연인",
            contents = "둘이서 가자"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "연인",
            contents = "좋아\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "연인",
            contents = "... ...\n좋아해"
        ),

        // 전차
        Persona3CommunitySelect(
            rank = 2,
            arcana = "전차",
            contents = "근성이 부족하다\n임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "전차",
            contents = "정말 괜찮아?\n나을것 같아?"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "전차",
            contents = "임의 선택\n고생했네"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "전차",
            contents = "병원 갔다 오는 길이야?\n내 어깨에 기대"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "전차",
            contents = "근성이야\n왜 그렇게까지 해?\n네 무릎은 어쩌고?"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "전차",
            contents = "상태는 어때?\n근성이야"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "전차",
            contents = "... ..."
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "전차",
            contents = "괜찮아"
        ),

        // 정의
        Persona3CommunitySelect(
            rank = 2,
            arcana = "정의",
            contents = "괜찮아\n만화책을 자주 읽어\n즐거워\n임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "정의",
            contents = "파렴치해\n찬성이야"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "정의",
            contents = "임의 선택\n곁에 있을게"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "정의",
            contents = "뭐든 물어봐\n연애 감정이야\n치히로의 상담이라면 환영이야\n손을 잡는다"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "정의",
            contents = "재미있어?\n무슨 뜻이야?"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "정의",
            contents = "뭔가 오해가 있는 것 같아\n어떻게든 해야겠어..."
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "정의",
            contents = "임의 선택\n난 알아"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "정의",
            contents = "힘이 되어 줄거야\n임의 선택\n임의 선택\n갑자기 왜 그래?\n치히로와 같은 마음이야\n임의 선택"
        ),

        // 은둔자
        Persona3CommunitySelect(
            rank = 2,
            arcana = "은둔자",
            contents = "기억해\n인도어파야?"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "은둔자",
            contents = "무슨 일 있었어?\n일이 싫어?"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "은둔자",
            contents = "결혼하자"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "은둔자",
            contents = "멍청이이이이이!\nY양은 선생님이야?"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "은둔자",
            contents = "그 녀석이라니?"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "은둔자",
            contents = "젊어야지\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "은둔자",
            contents = "얘기할 생각은 있어?\n어떤 애야?"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "은둔자",
            contents = "이건 아니지\n임의 선택\n어쩌려고?"
        ),

        // 운명
        Persona3CommunitySelect(
            rank = 2,
            arcana = "운명",
            contents = "임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "운명",
            contents = "히라가의 실력이야\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "운명",
            contents = "임의 선택\n여기서 이래봐야 소용없어"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "운명",
            contents = "임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "운명",
            contents = "임의 선택\n마음대로 해"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "운명",
            contents = "의사가 되려고?"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "운명",
            contents = "괜찮아\n임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "운명",
            contents = "가면 안 돼\n임의 선택 all"
        ),

        // 힘
        Persona3CommunitySelect(
            rank = 2,
            arcana = "힘",
            contents = "무슨 일 있었어?\n어쩔 수 없지\n그래 맞아"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "힘",
            contents = "임의 선택\n신경 쓰지 마\n영광이야"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "힘",
            contents = "임의 선택\n물론이지"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "힘",
            contents = "잘 가르쳐서 그래\n유우코를 믿어"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "힘",
            contents = "임의 선택\n믿자"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "힘",
            contents = "애들 말대로 할까?\n후련해?\n하자"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "힘",
            contents = "임의 선택\n임의 선택\n여자아이"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "힘",
            contents = "임의 선택\n스포츠 강사?\n임의 선택\n좋아하니까\n임의 선택"
        ),

        // 사형수
        Persona3CommunitySelect(
            rank = 2,
            arcana = "사형수",
            contents = "가자\n임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "사형수",
            contents = "임의 선택\n분명 돌아올거야"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "사형수",
            contents = "다행이네"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "사형수",
            contents = "너무하네\n그렇지 않아"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "사형수",
            contents = "진정해\n그것만 있으면 괜찮아"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "사형수",
            contents = "임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "사형수",
            contents = "햄버거\n대단해\n아빠"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "사형수",
            contents = "계속 친구야"
        ),

        // 절제
        Persona3CommunitySelect(
            rank = 2,
            arcana = "절제",
            contents = "임의 선택\n동감이야"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "절제",
            contents = "임의 선택\n기모노는?"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "절제",
            contents = "기운이 없네\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "절제",
            contents = "가자\n일본에 머물러"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "절제",
            contents = "좀 쉬어\n임의 선택\n응원할게"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "절제",
            contents = "좋은 생각 같아"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "절제",
            contents = "인정해 주실 거야"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "절제",
            contents = "고국이 그립지 않아?"
        ),

        // 악마
        Persona3CommunitySelect(
            rank = 2,
            arcana = "악마",
            contents = "플라시보\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "악마",
            contents = "조금은 있어요"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "악마",
            contents = "있어요!"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "악마",
            contents = "그 녀석이라뇨?\n임의 선택\n돈 이야기만 하고..."
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "악마",
            contents = "재밌었겠네요"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "악마",
            contents = "그런 것 같기도...\n일그러진 오이"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "악마",
            contents = "짚이는 데 있나요?"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "악마",
            contents = "기부할 거에요?"
        ),

        // 탑
        Persona3CommunitySelect(
            rank = 2,
            arcana = "탑",
            contents = "당신과는 상관없어요\n뭐라고 부르면 돼요?"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "탑",
            contents = "친구 같은거 없어요"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "탑",
            contents = "미는 것도 좋겠네요"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "탑",
            contents = "의외로 어떻게든 돼요\n있어요"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "탑",
            contents = "돌아가는게 좋겠어요\n절에서 일하는 사람은 없어요?"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "탑",
            contents = "안 하는게 좋을 것 같아요"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "탑",
            contents = "아버지?\n꼭 말해야 해요?\n임의 선택\n도망치고 있는 거에요?"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "탑",
            contents = "임의 선택 all"
        ),

        // 별
        Persona3CommunitySelect(
            rank = 2,
            arcana = "별",
            contents = "부담되지 않아?\n나 자신이 라이벌이야"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "별",
            contents = "임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "별",
            contents = "임의 선택\n좋은 이야기인걸\n편해지실 거야"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "별",
            contents = "괜찮아\n또 먹으러 오자"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "별",
            contents = "임의 선택\n임의 선택\n임의 선택\n괜찮아"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "별",
            contents = "괜찮아\n임의 선택\n임의 선택\n포기하지 마"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "별",
            contents = "선물로 사 가\n신경 쓰지 마"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "별",
            contents = "이겼어?\n축하해\n임의 선택\n고마워"
        ),

        // 달
        Persona3CommunitySelect(
            rank = 2,
            arcana = "달",
            contents = "맞아"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "달",
            contents = "미식왕"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "달",
            contents = "어디 아파?"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "달",
            contents = "맞아"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "달",
            contents = "병 걸렸어?"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "달",
            contents = "멸망이라고?\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "달",
            contents = "그건 아닌 것 같은데"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "달",
            contents = "임의 선택"
        ),

        // 태양
        Persona3CommunitySelect(
            rank = 2,
            arcana = "태양",
            contents = "그래?\n일리가 있네"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "태양",
            contents = "모르겠어\n이제 그만 말해"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "태양",
            contents = "좋아해\n시시하면 보다 말아"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "태양",
            contents = "자각이 부족한 것 같아"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "태양",
            contents = "좋은데?\n재밌을 것 같아"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "태양",
            contents = "어두운 이야기네"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "태양",
            contents = "기다릴게\n끊는 이유가 뭐야?"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "태양",
            contents = "이야기를 다 쓴거야?"
        ),

        // 영겁
        Persona3CommunitySelect(
            rank = 2,
            arcana = "영겁",
            contents = "좋아해"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "영겁",
            contents = "임의 선택\n그렇지 않아"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "영겁",
            contents = "못봤어\n알았어\n임의 선택"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "영겁",
            contents = "그럴지도"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "영겁",
            contents = "아무 잘못 없어"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "영겁",
            contents = "임의 선택 all"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "영겁",
            contents = "지금이 그러고 있지\n임의 선택\n사랑이야"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "영겁",
            contents = "그렇네\n나도 좋아해"
        ),

        // 광대
        Persona3CommunitySelect(
            rank = 2,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "광대",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "광대",
            contents = "스토리 진행"
        ),

        // 사신
        Persona3CommunitySelect(
            rank = 2,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "사신",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "사신",
            contents = "스토리 진행"
        ),

        // 심판
        Persona3CommunitySelect(
            rank = 2,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 3,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 4,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 5,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 6,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 7,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 8,
            arcana = "심판",
            contents = "스토리 진행"
        ),
        Persona3CommunitySelect(
            rank = 9,
            arcana = "심판",
            contents = "스토리 진행"
        ),
    )