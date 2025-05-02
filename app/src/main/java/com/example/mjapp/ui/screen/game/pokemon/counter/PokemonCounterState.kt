package com.example.mjapp.ui.screen.game.pokemon.counter

import com.example.network.model.PokemonCounter

data class PokemonCounterState(
    val list: List<PokemonCounter> = listOf(),
    val selectIndex: Int = 0
) {
    fun getSelectPokemon() = list.getOrNull(selectIndex)
}