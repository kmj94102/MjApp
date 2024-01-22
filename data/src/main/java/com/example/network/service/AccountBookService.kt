package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountBookService {
    /** 가계부 등록 **/
    @POST("/accountBook/insert")
    suspend fun insertAccountBookItem(@Body item: AccountBookInsertItem): String

    /** 가계부 조회 **/
    @POST("/accountBook/select/info")
    suspend fun fetchAccountBookInfo(@Body item: DateConfiguration): AccountBookMainInfo

    /** 이번달 상세 조회 **/
    @POST("/accountBook/select/thisMonthDetail")
    suspend fun fetchThisMonthDetail(@Body item: DateConfiguration): AccountBookDetailInfo

    /** 고정 내역 등록 **/
    @POST("/accountBook/insert/fixed")
    suspend fun insertFixedAccountBookItem(@Body item: FixedAccountBook): String

    /** 고정 내역 삭제 **/
    @DELETE("/accountBook/delete/fixed")
    suspend fun deleteFixedAccountBookItem(@Query("id") id: Int)

    /** 고정 내역 조회 **/
    @POST("/accountBook/select/fixed")
    suspend fun fetchFixedAccountBookItem(): List<FixedAccountBook>

    /** 즐겨 찾기 조회 **/
    @POST("/accountBook/select/frequently")
    suspend fun fetchFrequentlyAccountBookItems(): List<FixedItem>

    /** 즐겨 찾기 삭제 **/
    @DELETE("/accountBook/delete/frequently")
    suspend fun deleteFrequentlyAccountBookItem(@Query("id") id: Int)

}