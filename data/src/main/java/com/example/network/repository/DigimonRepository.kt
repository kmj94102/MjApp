package com.example.network.repository

import com.example.network.model.DmoUnionDetail
import com.example.network.model.DmoUnionInfo
import kotlinx.coroutines.flow.Flow

interface DigimonRepository {
    fun fetchUnionList(): Flow<List<DmoUnionInfo>>
    fun fetchUnionDetail(id: Int): Flow<DmoUnionDetail>
}