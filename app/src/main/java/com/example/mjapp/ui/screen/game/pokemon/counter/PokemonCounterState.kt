package com.example.mjapp.ui.screen.game.pokemon.counter

import com.example.network.model.PokemonCounter

data class PokemonCounterUiState(
    val isCustomSettingDialogShow: Boolean = false,
    val isSearchDialogShow: Boolean = false,
    val selectValue: PokemonCounter = PokemonCounter.init()
)