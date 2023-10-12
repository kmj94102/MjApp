package com.example.network.service

import com.example.network.model.DayParam
import com.example.network.model.Examination
import com.example.network.model.VocabularyList
import retrofit2.http.Body
import retrofit2.http.POST

interface VocabularyService {

    @POST("/vocabulary/select")
    suspend fun fetchVocabularyList(@Body item: DayParam): VocabularyList

    @POST("/vocabulary/select/examination")
    suspend fun fetchExamination(@Body item: DayParam): List<Examination>

}