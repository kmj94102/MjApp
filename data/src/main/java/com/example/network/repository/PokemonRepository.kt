package com.example.network.repository

import androidx.paging.PagingData
import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    /** 포켓몬 추가 **/
    suspend fun insertPokemon(pokemonInfo: PokemonInfo): String

    /** 포켓몬 조회 **/
    fun fetchPokemonList(name: String): Flow<PagingData<PokemonSummary>>

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