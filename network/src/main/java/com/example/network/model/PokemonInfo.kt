package com.example.network.model

data class PokemonInfo(
    val number: String = "",
    val name: String = "",
    val status: String = "",
    val classification: String = "",
    val characteristic: String = "",
    val attribute: String = "",
    val image: String = "",
    val shinyImage: String = "",
    val spotlight: String = "",
    val description: String = "",
    val generation: Int = 0,
    val isCatch: Boolean = false
)

data class PokemonImageInfo(
    val number: String,
    val image: String,
    val shinyImage: String
)

data class PokemonDetailInfo(
    val pokemonInfo: PokemonInfo,
    val beforeInfo: PokemonImageInfo?,
    val nextInfo: PokemonImageInfo?
) {
    fun getWeekImageList(): List<Int> {
        val attributeList = pokemonInfo.attribute
            .split(",")
            .map { getWeaknessInfo(it) }

        val weekList = when (attributeList.size) {
            1 -> {
                attributeList[0]
            }
            2 -> {
                attributeList[0].zip(attributeList[1]).map { it.first * it.second }
            }
            else -> emptyList()
        }

        return weekList
            .zip(TypeInfo.values().map { it.imageRes })
            .filter { it.first >= 2f }
            .map { it.second }
    }

    fun getTyeInfoList() = pokemonInfo.attribute
        .split(",")
        .map { getTypeInfo(it) }

    fun getClassAndCharacter() =
        "${pokemonInfo.classification} | ${pokemonInfo.characteristic.replace(",", ", ")}"
}