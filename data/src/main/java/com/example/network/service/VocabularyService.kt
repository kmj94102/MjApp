package com.example.network.service

import com.example.network.model.DayParam
import com.example.network.model.VocabularyList
import retrofit2.http.Body
import retrofit2.http.POST

interface VocabularyService {

    @POST("/vocabulary/select")
    suspend fun fetchVocabularyList(@Body item: DayParam): VocabularyList

}