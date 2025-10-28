package com.example.network.service

import com.example.network.model.DmoUnionDetail
import com.example.network.model.DmoUnionInfo
import com.example.network.model.IdParam
import retrofit2.http.Body
import retrofit2.http.POST

interface DigimonService {
    @POST("/digimon/select/dmo/union/list")
    suspend fun fetchUnionList(): List<DmoUnionInfo>

    @POST("/digimon/select/union/detail")
    suspend fun fetchUnionDetail(@Body id: IdParam): DmoUnionDetail
}