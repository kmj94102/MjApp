package com.example.network.service

import javax.inject.Inject

class ExternalClient @Inject constructor(
    private val service: ExternalService
) {
    /** 포켓몬 상세 조회 **/
    suspend fun fetchPokemonDetail(index: Int) = runCatching { service.fetchPokemonDetail(index) }

    /** 포켓몬 스펙 조회 **/
    suspend fun fetchPokemonSpecies(index: Int) = runCatching { service.fetchPokemonSpecies(index) }

    /** 포켓몬 특성 조회 **/
    suspend fun fetchAbility(ability: String) = runCatching { service.fetchAbility(ability) }
}