package com.example.network.repository

import com.example.network.model.ElswordCounterUpdateItem
import com.example.network.model.HomeInfoResult
import com.example.network.model.HomeParam
import com.example.network.model.UpdatePokemonCatch
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchHomeInfo(item: HomeParam): Flow<HomeInfoResult>

    suspend fun deleteSchedule(id: Int)

    suspend fun deletePlanTasks(id: Int)

    suspend fun updateCounter(count: Int, number: String)

    suspend fun deletePokemonCounter(index: Int)

    suspend fun updateCatch(number: String)

    /** 포켓몬 잡은 상태 업데이트 **/
    suspend fun updatePokemonCatch(pokemonCatch: UpdatePokemonCatch): Result<String>

    suspend fun updateCustomIncrease(customIncrease: Int, number: String)

    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int

}