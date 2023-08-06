package com.example.network.repository

import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    /** 포켓몬 추가 **/
    suspend fun insertPokemon(pokemonInfo: PokemonInfo): String

    /** 포켓몬 조회 **/
    fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
    ): Flow<Pair<Boolean, List<PokemonSummary>>>

    /** 포켓몬 상세 조회 **/
    fun fetchPokemonDetailInfo(number: String): Flow<PokemonDetailInfo>

    /** 포켓몬 잡은 상태 업데이트 **/
    suspend fun updatePokemonCatch(pokemonCatch: UpdatePokemonCatch): Result<String>

    /** 포켓몬 카운터 추가 **/
    suspend fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo): Result<Unit>

    suspend fun insertPokemonCounter(number: String)

    /** 포켓몬 카운터 조회 **/
    fun fetchPokemonCounter(): Flow<List<PokemonCounter>>

    /** 포켓몬 카운터 삭제 **/
    suspend fun deletePokemonCounter(number: String)

    /** 카운터 업데이트 **/
    suspend fun updateCounter(count: Int, number: String)

    /** 증가 폭 업데이트 **/
    suspend fun updateCustomIncrease(customIncrease: Int, number: String)

    /** 잡기 상태 업데이트 **/
    suspend fun updateCatch(number: String)

    /** 포켓몬 간략한 조회 (진화 추가용) **/
    fun fetchBriefPokemonList(search: String): Flow<List<BriefPokemonItem>>

    /** 포켓몬 진화 추가 **/
    suspend fun insertPokemonEvolution(
        evolutions: List<PokemonEvolution>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    /**
     * 포켓몬 기존 spotlight 조회
     * **/
    fun fetchPokemonBeforeSpotlights(): Flow<List<PokemonSpotlightItem>>

    /**
     * 포켓몬 spotlight 업데이트
     * **/
    suspend fun updatePokemonSpotlight(
        item: PokemonSpotlightItem
    ): String
}