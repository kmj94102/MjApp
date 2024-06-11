package com.example.mjapp.ui.screen.game.pokemon.dex

import com.example.network.model.PokemonSummary

data class PokemonDexUiState(
    val isDetailDialogShow: Boolean = false,
    val isSearchDialogShow: Boolean = false
)

data class PokemonDexState(
    val selectNumber: String = "",
    val isShiny: Boolean = false,
    val search: String = "",
    val list: List<PokemonSummary> = listOf(),
    val isMoreDate: Boolean = true
)