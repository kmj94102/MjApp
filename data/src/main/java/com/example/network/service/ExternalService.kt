package com.example.network.service

import com.example.network.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ExternalService {
    /** 포켓몬 정보 조회 **/
    @GET("v2/pokemon/{index}")
    suspend fun fetchPokemonDetail(
        @Path("index") index: Int = 1
    ): ExternalPokemonInfo

    /** 포켓몬 이름, 종류 조회 **/
    @GET("v2/pokemon-species/{index}")
    suspend fun fetchPokemonSpecies(
        @Path("index") index: Int = 1
    ): SpeciesInfo

    /** 포켓몬 특성 조회 **/
    @GET("v2/ability/{ability}")
    suspend fun fetchAbility(
        @Path("ability") ability: String
    ): AbilityInfo
}