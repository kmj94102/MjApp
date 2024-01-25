package com.example.network.model

import com.example.network.database.entity.PokemonCounterEntity
import java.util.Calendar

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
) {
    fun toPokemonCounterEntity() = PokemonCounterEntity(
        name = name,
        number = number,
        image = image,
        shinyImage = shinyImage,
        count = 0,
        timestamp = Calendar.getInstance().timeInMillis
    )
}

data class EvolutionInfo(
    val beforeDot: String,
    val beforeShinyDot: String,
    val beforeNumber: String,
    val afterDot: String,
    val afterShinyDot: String,
    val afterNumber: String,
    val evolutionImage: String,
    val evolutionCondition: String
)

data class PokemonImageInfo(
    val number: String,
    val image: String,
    val shinyImage: String
)

data class PokemonDetailInfo(
    val pokemonInfo: PokemonInfo,
    val beforeInfo: PokemonImageInfo?,
    val nextInfo: PokemonImageInfo?,
    val evolutionInfo: List<EvolutionInfo>
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

    fun toPokemonCounterEntity() = PokemonCounterEntity(
        name = pokemonInfo.name,
        number = pokemonInfo.number,
        image = pokemonInfo.image,
        shinyImage = pokemonInfo.shinyImage,
        count = 0,
        timestamp = Calendar.getInstance().timeInMillis
    )
}