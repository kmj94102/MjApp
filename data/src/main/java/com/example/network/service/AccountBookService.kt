package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountBookService {
    @POST("/insert/accountBook")
    suspend fun insertAccountBookItem(@Body item: AccountBookInsertItem): String

    @POST("/select/accountBook/info")
    suspend fun fetchAccountBookInfo(@Body item: DateConfiguration): AccountBookMainInfo

    @POST("/select/accountBook/thisMonthDetail")
    suspend fun fetchThisMonthDetail(@Body item: DateConfiguration): AccountBookDetailInfo

    @POST("/insert/accountBook/fixed")
    suspend fun insertFixedAccountBookItem()
}