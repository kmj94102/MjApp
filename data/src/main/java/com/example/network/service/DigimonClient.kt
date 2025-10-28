package com.example.network.service

import com.example.network.model.IdParam
import javax.inject.Inject

class DigimonClient @Inject constructor(
    private val service: DigimonService
) {
    suspend fun fetchUnionList() = runCatching { service.fetchUnionList() }
    suspend fun fetchUnionDetail(id: Int) = runCatching { service.fetchUnionDetail(IdParam(id)) }
}