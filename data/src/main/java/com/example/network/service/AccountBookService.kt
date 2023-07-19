package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountBookService {
    @POST("/insert/accountBook")
    suspend fun insertAccountBookItem(@Body item: AccountBookInsertItem): String

    @POST("/select/accountBook/summaryThisMonth")
    suspend fun fetchSummaryThisMonth(@Body item: DateConfiguration): SummaryAccountBookThisMonthInfo

    @POST("/select/accountBook/thisMonthDetail")
    suspend fun fetchThisMonthDetail(@Body item: DateConfiguration): AccountBookDetailInfo
}