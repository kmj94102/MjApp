package com.example.network.service

import com.example.network.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ElswordService {
    /** 퀘스트 등록 **/
    @POST("/insert/elsword/quest")
    suspend fun insertQuest(@Body item: ElswordQuest): String

    /** 퀘스트 삭제 **/
    @DELETE("/delete/elsword/quest")
    suspend fun deleteQuest(@Query("id") id: Int)

    /** 퀘스트 조회 **/
    @GET("/select/elsword/quest")
    suspend fun fetchQuestList(): List<ElswordQuestSimpleResult>

    /** 퀘스트 상세 조회 **/
    @GET("/select/elsword/quest/detail")
    suspend fun fetchQuestDetailList(): List<ElswordQuestDetailResult>

    /** 퀘스트 업데이트 **/
    @POST("/update/elsword/quest")
    suspend fun updateQuest(@Body item: ElswordQuestUpdate)

    /** 퀘스트 카운터 업데이트 **/
    @POST("/update/elsword/counter")
    suspend fun updateQuestCounter(@Body item: ElswordCounterUpdateItem): Int
}