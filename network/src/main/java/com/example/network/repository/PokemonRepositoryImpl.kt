package com.example.network.repository

import com.example.network.model.CharacteristicInfo
import com.example.network.model.PokemonInfo
import com.example.network.model.PokemonSummary
import com.example.network.model.PokemonSummaryResult
import com.example.network.service.ExternalClient
import com.example.network.service.PokemonClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val client: PokemonClient,
    private val externalClient: ExternalClient
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
}