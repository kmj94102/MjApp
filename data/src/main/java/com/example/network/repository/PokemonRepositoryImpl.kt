package com.example.network.repository

import android.util.Log
import com.example.network.database.dao.PokemonDao
import com.example.network.model.*
import com.example.network.service.ExternalClient
import com.example.network.service.PokemonClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val client: PokemonClient,
    private val externalClient: ExternalClient,
    private val dao: PokemonDao
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

    override suspend fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
        onResult: (Boolean, List<PokemonSummary>) -> Unit
    ) {
        val result = client.fetchPokemonList(name, skip * limit, limit)

        onResult(
            result.getIsMoreData(skip * limit),
            result.getMappingList()
        )
    }

    override fun fetchPokemonDetailInfo(number: String) = flow {
        emit(
            client.fetchPokemonDetailInfo(number)
        )
    }

    override suspend fun updatePokemonCatch(
        pokemonCatch: UpdatePokemonCatch
    ): String {
        return try {
            client.updatePokemonCatch(pokemonCatch)
        } catch (e: Exception) {
            Log.e("updatePokemonCatch", "error : ${e.message}")
            "업데이트 실패"
        }
    }

    override suspend fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo) {
        dao.insertPokemonCounter(
            pokemonDetailInfo.toPokemonCounterEntity()
        )
    }

    override fun fetchPokemonCounter(): Flow<List<PokemonCounter>> =
        dao.fetchPokemonCounter()

    override suspend fun updateCounter(count: Int, number: String) {
        dao.updateCounter(count, number)
    }

    override suspend fun updateCustomIncrease(customIncrease: Int, number: String) {
        dao.updateCustomIncrease(customIncrease, number)
    }

    override suspend fun deletePokemonCounter(number: String) {
        dao.deleteCounter(number)
    }

    override suspend fun updateCatch(number: String) {
        dao.updateCatch(number)
    }

    override fun fetchBriefPokemonList(search: String) = flow {
        try {
            emit(
                client.fetchBriefPokemonList(search).mapNotNull { it.toBriefPokemonItem() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
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
}