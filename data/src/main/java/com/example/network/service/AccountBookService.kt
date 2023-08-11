package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountBookService {
    /** 가계부 조회 **/
    @POST("/select/accountBook/info")
    suspend fun fetchAccountBookInfo(@Body item: DateConfiguration): AccountBookMainInfo
    /** 가계부 등록 **/
    @POST("/insert/accountBook")
    suspend fun insertAccountBookItem(@Body item: AccountBookInsertItem): String

    /** 이번달 상세 조회 **/
    @POST("/select/accountBook/thisMonthDetail")
    suspend fun fetchThisMonthDetail(@Body item: DateConfiguration): AccountBookDetailInfo

    /** 고정 내역 등록 **/
    @POST("/insert/accountBook/fixed")
    suspend fun insertFixedAccountBookItem(@Body item: FixedAccountBook): String

    /** 고정 내역 삭제 **/
    @DELETE("/delete/accountBook/fixed")
    suspend fun deleteFixedAccountBookItem(@Query("id") id: Int)

    /** 고정 내역 조회 **/
    @POST("/select/accountBook/fixed")
    suspend fun fetchFixedAccountBookItem(): List<FixedAccountBook>

    /** 즐겨 찾기 조회 **/
    @POST("/select/accountBook/frequently")
    suspend fun fetchFrequentlyAccountBookItems(): List<FrequentlyItem>

    /** 즐겨 찾기 삭제 **/
    @DELETE("/delete/accountBook/frequently")
    suspend fun deleteFrequentlyAccountBookItem(@Query("id") id: Int)

}