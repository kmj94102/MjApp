package com.example.network.service

import com.example.network.model.AccountBookItem
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.DateConfiguration
import com.example.network.model.SummaryAccountBookThisMonthInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountBookService {
    @POST("/insert/accountBook")
    suspend fun insertAccountBookItem(@Body item: AccountBookItem): String

    @POST("/select/accountBook/summaryThisMonth")
    suspend fun fetchSummaryThisMonth(@Body item: DateConfiguration): SummaryAccountBookThisMonthInfo

    @POST("/select/accountBook/thisMonthDetail")
    suspend fun fetchThisMonthDetail(@Body item: DateConfiguration): AccountBookDetailInfo
}