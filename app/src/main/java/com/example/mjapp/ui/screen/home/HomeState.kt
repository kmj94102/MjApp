package com.example.mjapp.ui.screen.home

import com.example.network.model.HomeInfoResult
import com.example.network.model.MyCalendar
import com.example.network.model.PokemonCounter

data class HomeState(
    val homeInfo: HomeInfoResult = HomeInfoResult(),
    val list: List<MyCalendar> = listOf(),
    val pokemonSelectIndex: Int = 0,
    val index: Int = 0
) {
    private fun getPokemonCounterList() = homeInfo.pokemonInfo

    fun getPokemonCounterListSize() = getPokemonCounterList().size

    fun getPokemonCounterProgress() =
        "${pokemonSelectIndex + 1}/${getPokemonCounterListSize()}"

    fun getPokemonCounter(): PokemonCounter? = runCatching {
        getPokemonCounterList()[pokemonSelectIndex]
    }.getOrNull()

    fun getElswordQuestList() = homeInfo.questInfo

    fun getSelectCalendarItem() =
        list.getOrElse(index) { MyCalendar() }
}