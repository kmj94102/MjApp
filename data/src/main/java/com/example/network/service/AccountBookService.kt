package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountBookService {
    @POST("/insert/accountBook")
    suspend fun insertAccountBookItem(@Body item: AccountBookInsertItem): String

    @DELETE("/delete/accountBook/fixed")
    suspend fun deleteFixedAccountBookItem(@Query("id") id: Int)

    @POST("/select/accountBook/info")
    suspend fun fetchAccountBookInfo(@Body item: DateConfiguration): AccountBookMainInfo

    @POST("/select/accountBook/thisMonthDetail")
    suspend fun fetchThisMonthDetail(@Body item: DateConfiguration): AccountBookDetailInfo

    @POST("/insert/accountBook/fixed")
    suspend fun insertFixedAccountBookItem(@Body item: FixedAccountBook): String

    @POST("/select/accountBook/fixed")
    suspend fun fetchFixedAccountBookItem(): List<FixedAccountBook>

}