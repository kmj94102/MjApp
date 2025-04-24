package com.example.mjapp.ui.screen.game.pokemon.counter

import com.example.network.model.PokemonCounter

data class PokemonCounterUiState(
    val isCustomSettingDialogShow: Boolean = false,
    val isSearchDialogShow: Boolean = false,
    val selectValue: PokemonCounter = PokemonCounter.init()
)

data class PokemonCounterState(
    val list: List<PokemonCounter> = listOf(),
    val selectIndex: Int = 0
) {
    fun getSelectPokemon() = list.getOrNull(selectIndex)
}