package com.example.network.repository

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

    override fun fetchPokemonList(skip: Int) = flow {
        val result = client.fetchPokemonList(skip = skip)
        val pokemonSummary = PokemonSummaryResult(
            list = result.list?.mapNotNull { it.toPokemonSummary() } ?: listOf(),
            isLast = (result.totalSize ?: 0) <= (result.list?.map { it.index }?.last() ?: 0)
        )

        emit(pokemonSummary)
    }

    override fun fetchPokemonDetailInfo(number: String) = flow {
        emit(
            client.fetchPokemonDetailInfo(number)
        )
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

    override suspend fun updateCatch(number: String) {
        dao.updateCatch(number)
    }
}