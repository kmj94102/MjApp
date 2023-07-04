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

    /**
     * 포켓몬 간략한 조회 (진화 추가용)
     * **/
    @GET("/select/pokemon/brief-list/{search}")
    suspend fun fetchBriefPokemonList(@Path("search") search: String): List<BriefPokemonInfo>

    /**
     * 포켓몬 진화 추가
     **/
    @POST("/insert/pokemon/evolution")
    suspend fun insertPokemonEvolution(@Body item: List<PokemonEvolution>): String

    /**
     * 포켓몬 기존 spotlight 조회
     * **/
    @POST("/select/pokemon/before-image-info")
    suspend fun fetchPokemonBeforeSpotlights(): List<PokemonSpotlightItem>

    /**
     * 포켓몬 spotlight 업데이트
     * **/
    @POST("/update/pokemon/image")
    suspend fun updatePokemonSpotlight(@Body item: PokemonSpotlightItem): String

}