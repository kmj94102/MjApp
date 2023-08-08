package com.example.network.repository

import com.example.network.model.ElswordCounterUpdateItem
import com.example.network.model.HomeInfoResult
import com.example.network.model.HomeParam
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchHomeInfo(item: HomeParam): Flow<HomeInfoResult>

    suspend fun deleteSchedule(id: Int)

    suspend fun deletePlanTasks(id: Int)

    suspend fun updateCounter(count: Int, number: String)

    suspend fun deletePokemonCounter(number: String)

    suspend fun updateCatch(number: String)

    suspend fun updateCustomIncrease(customIncrease: Int, number: String)

    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int

}