package com.example.network.model

data class PokemonListResult(
    val list: List<PokemonListInfo>,
    val totalSize: Int?
) {
    fun getMappingList() = list.mapNotNull { it.toPokemonSummary() }

    fun getIsMoreData(currentIndex: Int) = (totalSize ?: 0) > currentIndex
}

data class PokemonListInfo(
    val index: Int?,
    val number: String?,
    val name: String?,
    val spotlight: String?,
    val shinySpotlight: String?,
    val isCatch: Boolean?
) {
    fun toPokemonSummary(): PokemonSummary? {
        return PokemonSummary(
            index = index ?: return null,
            number = number ?: return null,
            name = name ?: return null,
            spotlight = spotlight ?: return null,
            shinySpotlight = shinySpotlight ?: return null,
            isCatch = isCatch ?: false
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
    val shinySpotlight: String,
    val isCatch: Boolean
)