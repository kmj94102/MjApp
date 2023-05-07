package com.example.network.service

import com.example.network.model.CharacteristicInfo
import com.example.network.model.PokemonInfo
import com.example.network.model.PokemonListResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PokemonService {

    /** 포켓몬 조회 **/
    @GET("pokemonList")
    suspend fun fetchPokemonList(@Query("skip") skip: Int, @Query("limit") limit: Int): PokemonListResult

    /** 포켓몬 추가 **/
    @POST("insert/pokemon")
    suspend fun insertPokemon(@Body item: PokemonInfo): String

    /** 특성 추가 **/
    @POST("insert/char")
    suspend fun insertCharacteristic(@Body item: CharacteristicInfo): String

}