package com.example.network.service

import com.example.network.model.ElswordQuest
import com.example.network.model.ElswordQuestSimpleResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ElswordService {
    @POST("/insert/elsword/quest")
    suspend fun insertQuest(@Body item: ElswordQuest): String

    @DELETE("/delete/elsword/quest")
    suspend fun deleteQuest(@Query("id") id: Int)

    @GET("/select/elsword/quest")
    suspend fun fetchQuestList(): List<ElswordQuestSimpleResult>
}