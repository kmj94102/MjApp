package com.example.network.repository

import com.example.network.model.PokemonDetailInfo
import com.example.network.model.PokemonInfo
import com.example.network.model.PokemonSummaryResult
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    /** 포켓몬 추가 **/
    suspend fun insertPokemon(pokemonInfo: PokemonInfo): String

    /** 포켓몬 조회 **/
    fun fetchPokemonList(skip: Int): Flow<PokemonSummaryResult>

    /** 포켓몬 상세 조회 **/
    fun fetchPokemonDetailInfo(number: String): Flow<PokemonDetailInfo>
}