package com.example.network.service

import javax.inject.Inject

class ExternalClient @Inject constructor(
    private val service: ExternalService
) {
    suspend fun fetchPokemonDetail(
        index: Int
    ) = service.fetchPokemonDetail(index)

    suspend fun fetchPokemonSpecies(
        index: Int
    ) = service.fetchPokemonSpecies(index)

    suspend fun fetchAbility(
        ability: String
    ) = service.fetchAbility(ability)
}