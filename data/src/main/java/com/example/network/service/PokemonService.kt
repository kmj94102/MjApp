package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    /** 포켓몬 조회 **/
    @GET("pokemonList")
    suspend fun fetchPokemonList(
        @Query("name") name: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): PokemonListResult

    /** 포켓몬 상세 조회 **/
    @GET("/pokemon/detail/{number}")
    suspend fun fetchPokemonDetail(@Path("number") number: String): PokemonDetailInfo

    /** 포켓몬 추가 **/
    @POST("insert/pokemon")
    suspend fun insertPokemon(@Body item: PokemonInfo): String

    /** 포켓몬 잡은 상태 업데이트 **/
    @POST("/update/pokemon/catch")
    suspend fun updatePokemonCatch(@Body item: UpdatePokemonCatch): String

    /** 특성 추가 **/
    @POST("insert/char")
    suspend fun insertCharacteristic(@Body item: CharacteristicInfo): String

}