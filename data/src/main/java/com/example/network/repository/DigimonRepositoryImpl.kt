package com.example.network.repository

import com.example.network.model.DmoUnionDetail
import com.example.network.model.DmoUnionInfo
import com.example.network.service.DigimonClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DigimonRepositoryImpl @Inject constructor(
    private val client: DigimonClient
): DigimonRepository {
    override fun fetchUnionList(): Flow<List<DmoUnionInfo>> = flow {
        emit(client.fetchUnionList().getOrThrow())
    }

    override fun fetchUnionDetail(id: Int): Flow<DmoUnionDetail> = flow {
        emit(client.fetchUnionDetail(id).getOrThrow())
    }
}