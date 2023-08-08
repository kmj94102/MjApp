package com.example.network.repository

import com.example.network.model.BriefPokemonItem
import com.example.network.model.CharacteristicInfo
import com.example.network.model.PokemonCounter
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.PokemonEvolution
import com.example.network.model.PokemonInfo
import com.example.network.model.PokemonSpotlightItem
import com.example.network.model.UpdatePokemonCatch
import com.example.network.model.getFailureThrow
import com.example.network.service.ExternalClient
import com.example.network.service.PokemonClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val client: PokemonClient,
    private val externalClient: ExternalClient,
) : PokemonRepository {
    override suspend fun insertPokemon(pokemonInfo: PokemonInfo): String {
        val originCharacteristics = pokemonInfo.characteristic.split(",")
        val characteristics = mutableListOf<CharacteristicInfo>()
        val result = ""

        originCharacteristics.forEach {
            externalClient.fetchAbility(it).mapper()?.let { info ->
                characteristics.add(info)
            }
        }

        characteristics.forEach {
            result.plus(client.insertCharacteristic(it)).plus("\n")
        }
        result.plus(
            client.insertPokemon(
                pokemonInfo.copy(
                    characteristic = characteristics.map { it.name }.reduce { acc, s -> "$acc,$s" }
                )
            )
        )
        return result
    }

    override fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
    ) = flow {
        client.fetchPokemonList(name, skip * limit, limit)
            .onSuccess {
                emit(
                    Pair(it.getIsMoreData(skip * limit), it.getMappingList())
                )
            }
            .getFailureThrow()
    }

    override fun fetchPokemonDetailInfo(number: String) = flow {
        client.fetchPokemonDetailInfo(number)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    override suspend fun updatePokemonCatch(
        pokemonCatch: UpdatePokemonCatch
    ) = client.updatePokemonCatch(pokemonCatch)
        .getFailureThrow()

    override suspend fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo) = runCatching {
        client.insertPokemonCounter(pokemonDetailInfo.toPokemonCounterEntity())
    }

    override suspend fun insertPokemonCounter(number: String) {
        client
            .fetchPokemonWithNumber(number)
            .onSuccess {
                client.insertPokemonCounter(it.toPokemonCounterEntity())
            }
            .getFailureThrow()
    }

    override fun fetchPokemonCounter(): Flow<List<PokemonCounter>> =
        client.fetchPokemonCounter()

    override suspend fun updateCounter(count: Int, number: String) {
        client.updateCounter(count, number)
    }

    override suspend fun updateCustomIncrease(customIncrease: Int, number: String) {
        client.updateCustomIncrease(customIncrease, number)
    }

    override suspend fun deletePokemonCounter(number: String) {
        client.deletePokemonCounter(number)
    }

    override suspend fun updateCatch(number: String) {
        client.updateCatch(number)
    }

    override fun fetchBriefPokemonList(search: String): Flow<List<BriefPokemonItem>> = flow {
        client
            .fetchBriefPokemonList(search)
            .onSuccess { list ->
                emit(list.mapNotNull { it.toBriefPokemonItem() })
            }
            .getFailureThrow()
    }

    override suspend fun insertPokemonEvolution(
        evolutions: List<PokemonEvolution>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            client.insertPokemonEvolution(evolutions)
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure()
        }
    }

    override fun fetchPokemonBeforeSpotlights() = flow {
        runCatching {
            emit(client.fetchPokemonBeforeSpotlights())
        }.onFailure {
            it.printStackTrace()
        }
    }

    override suspend fun updatePokemonSpotlight(item: PokemonSpotlightItem): String {
        return runCatching {
            client.updatePokemonSpotlight(item)
        }.getOrElse {
            it.printStackTrace()
            "업데이트 실패"
        }
    }
}