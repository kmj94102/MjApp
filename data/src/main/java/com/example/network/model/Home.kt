package com.example.network.model

data class HomeParam(
    val startDate: String,
    val endDate: String
)

data class HomeInfoResult(
    val calendarInfo: List<CalendarResult> = listOf(),
    val questInfo: List<ElswordCounter> = listOf(),
    val pokemonInfo: List<PokemonCounter> = listOf()
)