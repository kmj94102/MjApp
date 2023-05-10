package com.example.network.model

data class PokemonListResult(
    val list: List<PokemonListInfo>,
    val totalSize: Int?
)

data class PokemonListInfo(
    val index: Int?,
    val number: String?,
    val name: String?,
    val spotlight: String?,
    val shinySpotlight: String?
) {
    fun toPokemonSummary(): PokemonSummary? {
        return PokemonSummary(
            index = index ?: return null,
            number = number ?: return null,
            name = name ?: return null,
            spotlight = spotlight ?: return null,
            shinySpotlight = shinySpotlight ?: return null
        )
    }
}

data class PokemonSummaryResult(
    val list: List<PokemonSummary>,
    val isLast: Boolean
)

data class PokemonSummary(
    val index: Int,
    val number: String,
    val name: String,
    val spotlight: String,
    val shinySpotlight: String
)