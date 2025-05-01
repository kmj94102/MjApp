package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    /** 포켓몬 번호로 정보 조회 **/
    @GET("/pokemon/select")
    suspend fun fetchPokemonWithNumber(@Query("number") number: String): PokemonInfo

    /** 포켓몬 리스트 조회 **/
    @GET("/pokemon/select/list")
    suspend fun fetchPokemonList(
        @Query("name") name: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
        @Query("generations") generations: String,
        @Query("types") types: String,
        @Query("is_catch") isCatch: String
    ): PokemonListResult

    /** 포켓몬 상세 조회 **/
    @GET("/pokemon/select/detail/{number}")
    suspend fun fetchPokemonDetail(@Path("number") number: String): PokemonDetailInfo

    /** 포켓몬 추가 **/
    @POST("/pokemon/insert")
    suspend fun insertPokemon(@Body item: PokemonInfo): String

    /** 포켓몬 잡은 상태 업데이트 **/
    @POST("/pokemon/update/catch")
    suspend fun updatePokemonCatch(@Body item: UpdatePokemonCatch): String

    /** 특성 추가 **/
    @POST("/pokemon/insert/char")
    suspend fun insertCharacteristic(@Body item: CharacteristicInfo): String

    /**
     * 포켓몬 간략한 조회 (진화 추가용)
     * **/
    @GET("/pokemon/select/briefList/{search}")
    suspend fun fetchBriefPokemonList(@Path("search") search: String): List<BriefPokemonInfo>

    /**
     * 포켓몬 진화 추가
     **/
    @POST("/pokemon/insert/evolution")
    suspend fun insertPokemonEvolution(@Body item: List<PokemonEvolution>): String

    /**
     * 포켓몬 기존 spotlight 조회
     * **/
    @POST("/pokemon/select/beforeImageInfo")
    suspend fun fetchPokemonBeforeSpotlights(): List<PokemonSpotlightItem>

    /**
     * 포켓몬 spotlight 업데이트
     * **/
    @POST("/pokemon/update/image")
    suspend fun updatePokemonSpotlight(@Body item: PokemonSpotlightItem): String

    /**
     * 포케못 타이틀 도감 별 카운트
     * **/
    @GET("/dex/select/count")
    suspend fun fetchGenerationCountList(): List<GenerationCount>

    /**
     * 선택한 타이틀 도감 포켓몬 리스트
     * **/
    @GET("/dex/select/list")
    suspend fun fetchGenerationList(
        @Query("generationIdx") index: Int
    ): List<GenerationDex>

    /**
     * 잡은 상태 업데이트
     * **/
    @POST("/dex/update/isCatch")
    suspend fun updateGenerationIsCatch(
        @Body item: GenerationUpdateParam
    ): Boolean

}