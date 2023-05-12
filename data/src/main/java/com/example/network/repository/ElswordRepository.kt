package com.example.network.repository

import com.example.network.model.ElswordCounter
import kotlinx.coroutines.flow.Flow

interface ElswordRepository {
    suspend fun insertCounter(name: String, max: Int)

    fun fetchCounterTitleList(): Flow<List<String>>

    fun fetchCounterList(): Flow<List<ElswordCounter>>

    suspend fun fetchCounter(id: Int): ElswordCounter

    suspend fun deleteCounter(id: Int)
}