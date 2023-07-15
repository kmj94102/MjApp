package com.example.network.service

import com.example.network.model.AccountBookItem
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountBookService {
    @POST("/insert/account_book")
    suspend fun insertAccountBookItem(@Body item: AccountBookItem): String
}