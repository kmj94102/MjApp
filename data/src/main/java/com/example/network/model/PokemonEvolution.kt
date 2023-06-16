package com.example.network.model

data class BriefPokemonInfo(
    val spotlight: String?,
    val number: String?
) {
    fun toBriefPokemonItem(): BriefPokemonItem? {
        return BriefPokemonItem(
            spotlight = spotlight ?: return null,
            number = number ?: return null
        )
    }
}

data class BriefPokemonItem(
    val spotlight: String,
    val number: String
)

data class PokemonEvolution(
    val numbers: String,
    val beforeNum: String,
    val afterNum: String,
    val evolutionType: String,
    val evolutionCondition: String
)

data class PokemonEvolutionItem(
    val beforeNum: String = "",
    val beforeImage: String = "",
    val afterNum: String = "",
    val afterImage: String = "",
    val evolutionType: String = "",
    val evolutionCondition: String = ""
) {
    fun toPokemonEvolution(numbers: String) = PokemonEvolution(
        numbers = numbers,
        beforeNum = beforeNum,
        afterNum = afterNum,
        evolutionType = evolutionType,
        evolutionCondition = evolutionCondition
    )
}

data class PokemonEvolutionCondition(
    val name: String,
    val image: String
) {
    fun getInitEvolutionCondition() = when(name) {
        "이상한사탕" -> "Lv."
        "친밀도" -> "친밀도 220 이상 레벨업"
        "다이맥스" -> "거다이맥스"
        else -> name
    }

    companion object {
        fun getEvolutionList() = listOf(
            PokemonEvolutionCondition(
                name = "이상한사탕",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rare-candy.png"
            ),
            PokemonEvolutionCondition(
                name = "메가진화",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/key-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "다이맥스",
                image = "https://w.namu.la/s/5b739c4aa36d2fd6bf73e0342bce10270f30d050a9e187424a2d0a38242a95e2ddcc81d5da07596562cc8e78bdd4cad7f45a3da47d3dcaea90af9fb80f6fde14128d7b1eefaf182f7acdd6a1836f24f41f67f2fdaeabb5960437a712ddf84bfc"
            ),
            PokemonEvolutionCondition(
                name = "친밀도",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/soothe-bell.png"
            ),
            PokemonEvolutionCondition(
                name = "가라두구머리장식",
                image = "https://static.wikia.nocookie.net/pokemon/images/3/3a/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EA%B0%80%EB%9D%BC%EB%91%90%EA%B5%AC%EB%A8%B8%EB%A6%AC%EC%9E%A5%EC%8B%9D.png/revision/latest?cb=20201122142600&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "가라두구팔찌",
                image = "https://static.wikia.nocookie.net/pokemon/images/f/f8/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EA%B0%80%EB%9D%BC%EB%91%90%EA%B5%AC%ED%8C%94%EC%B0%8C.png/revision/latest?cb=20200712152032&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "각성의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dawn-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "고운비늘",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/prism-scale.png"
            ),
            PokemonEvolutionCondition(
                name = "괴상한패치",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dubious-disc.png"
            ),
            PokemonEvolutionCondition(
                name = "금속코트",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png"
            ),
            PokemonEvolutionCondition(
                name = "깨진포트",
                image = "https://static.wikia.nocookie.net/pokemon/images/a/a8/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EA%B9%A8%EC%A7%84%ED%8F%AC%ED%8A%B8.png/revision/latest?cb=20191121192957&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "꽃사탕공예",
                image = "https://static.wikia.nocookie.net/pokemon/images/f/f4/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EA%BD%83%EC%82%AC%ED%83%95%EA%B3%B5%EC%98%88.png/revision/latest?cb=20191201141102&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "달의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/moon-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "달콤한사과",
                image = "https://static.wikia.nocookie.net/pokemon/images/d/d4/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EB%8B%AC%EC%BD%A4%ED%95%9C%EC%82%AC%EA%B3%BC.png/revision/latest?cb=20191119153004&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "동글동글돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/oval-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "리프의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/leaf-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "마그마부스터",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/magmarizer.png"
            ),
            PokemonEvolutionCondition(
                name = "물의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/water-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "변함없는 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png"
            ),
            PokemonEvolutionCondition(
                name = "불꽃의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "빛의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/shiny-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "새콤한사과",
                image = "https://static.wikia.nocookie.net/pokemon/images/f/fe/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EC%83%88%EC%BD%A4%ED%95%9C%EC%82%AC%EA%B3%BC.png/revision/latest?cb=20191119153002&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "심해의비늘",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/deep-sea-scale.png"
            ),
            PokemonEvolutionCondition(
                name = "심해의이빨",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/deep-sea-tooth.png"
            ),
            PokemonEvolutionCondition(
                name = "어둠의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dusk-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "얼음의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ice-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "업그레이드",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/up-grade.png"
            ),
            PokemonEvolutionCondition(
                name = "에레키부스터",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/electirizer.png"
            ),
            PokemonEvolutionCondition(
                name = "연결의끈",
                image = "https://static.wikia.nocookie.net/pokemon/images/3/3a/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EC%97%B0%EA%B2%B0%EC%9D%98%EB%81%88_%EB%A0%88%EC%95%84.png/revision/latest/scale-to-width-down/80?cb=20220228171418&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "영계의천",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/reaper-cloth.png"
            ),
            PokemonEvolutionCondition(
                name = "예리한손톱",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/razor-claw.png"
            ),
            PokemonEvolutionCondition(
                name = "예리한이빨",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/razor-fang.png"
            ),
            PokemonEvolutionCondition(
                name = "왕의징표석",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/kings-rock.png"
            ),
            PokemonEvolutionCondition(
                name = "용의비늘",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dragon-scale.png"
            ),
            PokemonEvolutionCondition(
                name = "주홍구슬",
                image = "https://static.wikia.nocookie.net/pokemon/images/a/a2/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EC%A3%BC%ED%99%8D%EA%B5%AC%EC%8A%AC_6.png/revision/latest?cb=20161029101720&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "쪽빛구슬",
                image = "https://static.wikia.nocookie.net/pokemon/images/9/91/%EB%8F%84%ED%8A%B8_%EC%95%84%EC%9D%B4%EC%BD%98_%EC%AA%BD%EB%B9%9B%EA%B5%AC%EC%8A%AC_6.png/revision/latest?cb=20161029101748&path-prefix=ko"
            ),
            PokemonEvolutionCondition(
                name = "천둥의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/thunder-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "태양의 돌",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sun-stone.png"
            ),
            PokemonEvolutionCondition(
                name = "통신진화",
                image = "https://github.com/kmj94102/PokemonDex/blob/master/app/src/main/res/drawable/img_communication_evolution.png?raw=true"
            ),
            PokemonEvolutionCondition(
                name = "프로텍터",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protector.png"
            ),
            PokemonEvolutionCondition(
                name = "향기주머니",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sachet.png"
            ),
            PokemonEvolutionCondition(
                name = "휘핑팝",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/whipped-dream.png"
            ),
        )
    }
}