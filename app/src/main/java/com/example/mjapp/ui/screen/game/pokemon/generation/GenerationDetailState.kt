package com.example.mjapp.ui.screen.game.pokemon.generation

import com.example.network.model.GenerationDex

data class GenerationDetailState(
    val selectNumber: String = "",
    val selectIdx: Int = 0,
    val isCatch: Boolean = false,
    val list: List<GenerationDex> = listOf(),
    val isDialogShow: Boolean = false
)