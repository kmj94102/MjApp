package com.example.mjapp.ui.screen.game.pokemon.dex

import com.example.network.model.PokemonSummary

data class PokemonDexState(
    val list: List<PokemonSummary> = listOf(),
    val isMoreDate: Boolean = true
)