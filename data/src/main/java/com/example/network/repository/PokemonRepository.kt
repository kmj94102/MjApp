package com.example.network.repository

import com.example.network.model.PokemonCounter
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

    /** 포켓몬 카운터 추가 **/
    suspend fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo)

    /** 포켓몬 카운터 조회 **/
    fun fetchPokemonCounter(): Flow<List<PokemonCounter>>

    /** 카운터 업데이트 **/
    suspend fun updateCounter(count: Int, number: String)

    /** 잡기 상태 업데이트 **/
    suspend fun updateCatch(number: String)
}