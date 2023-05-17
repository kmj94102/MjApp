package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 공휴일 정보 가져오기
     * @param from 일정을 조회할 기간의 시작 시각, UTC*, RFC5545의 DATE-TIME 형식(예: 2022-05-17T12:00:00Z)
     * @param to 일정을 조회할 기간의 종료 시각, from 이후 31일 이내의 값, UTC*, RFC5545의 DATE-TIME 형식(예: 2022-06-17T12:00:00Z)
     * **/
    @GET("https://kapi.kakao.com/v2/api/calendar/holidays")
    suspend fun fetchHolidays(
        @Header("Authorization") token: String = BuildConfig.KAKAO_ADMIN_AUTHORIZATION,
        @Query("from") from: String,
        @Query("to") to: String
    ): KakaoApiResult<HolidayItem>
}