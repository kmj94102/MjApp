package com.example.network.repository

import com.example.network.model.HolidayInfo
import com.example.network.model.PokemonInfo
import kotlinx.coroutines.flow.Flow


interface ExternalRepository {

    fun pokemonInfo(index: Int): Flow<PokemonInfo>

    fun fetchHolidays(year: String, month: String): Flow<List<HolidayInfo>>

}