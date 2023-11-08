package com.example.network.repository

import com.example.network.model.ElswordCounterUpdateItem
import com.example.network.model.HomeInfoResult
import com.example.network.model.HomeParam
import com.example.network.model.UpdatePokemonCatch
import com.example.network.model.getFailureThrow
import com.example.network.model.printStackTrace
import com.example.network.service.CalendarClient
import com.example.network.service.ElswordClient
import com.example.network.service.PokemonClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val calendarClient: CalendarClient,
    private val pokemonClient: PokemonClient,
    private val elswordClient: ElswordClient
) : HomeRepository {

    override fun fetchHomeInfo(item: HomeParam): Flow<HomeInfoResult> {
        val serverFlow = flow {
            calendarClient
                .fetchHomeInfo(item)
                .onSuccess { emit(it) }
                .getFailureThrow()
        }
        val pokemonFlow = pokemonClient
            .fetchPokemonCounter()
            .getOrElse { emptyFlow() }

        return serverFlow.combine(pokemonFlow) { server, pokemon ->
            server.copy(pokemonInfo = pokemon)
        }
    }

    override suspend fun deleteSchedule(id: Int) {
        calendarClient.deleteSchedule(id).getFailureThrow()
    }

    override suspend fun deletePlanTasks(id: Int) {
        calendarClient.deletePlanTasks(id).getFailureThrow()
    }

    override suspend fun updateCounter(count: Int, number: String) {
        pokemonClient.updateCounter(count, number)
    }

    override suspend fun deletePokemonCounter(index: Int) {
        pokemonClient.deletePokemonCounter(index)
    }

    override suspend fun updateCatch(number: String) {
        pokemonClient.updateCatch(number)
        pokemonClient.updatePokemonCatch(
            UpdatePokemonCatch(
                number = number,
                isCatch = true
            )
        ).getFailureThrow()
    }

    /** 포켓몬 잡은 상태 업데이트 **/
    override suspend fun updatePokemonCatch(
        pokemonCatch: UpdatePokemonCatch
    ) = pokemonClient.updatePokemonCatch(pokemonCatch).getFailureThrow()

    override suspend fun updateCustomIncrease(customIncrease: Int, number: String) {
        pokemonClient.updateCustomIncrease(customIncrease, number)
    }

    override suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int =
        elswordClient
            .updateQuestCounter(item)
            .printStackTrace()
            .getOrElse { 0 }

}