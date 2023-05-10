package com.example.network.service

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.network.model.CharacteristicInfo
import com.example.network.model.PokemonInfo
import com.example.network.paging_source.PokemonDexPagingSource
import javax.inject.Inject

class PokemonClient @Inject constructor(
    private val service: PokemonService
) {
    /** 포켓몬 조회 **/
    fun fetchPokemonList() = Pager(
        config = PagingConfig(
            pageSize = 100
        ),
        pagingSourceFactory = {
            PokemonDexPagingSource(pokemonService = service)
        }
    ).flow

    /** 포켓몬 상세 조회 **/
    suspend fun fetchPokemonDetailInfo(
        number: String
    ) = service.fetchPokemonDetail(number)

    /** 포켓몬 추가 **/
    suspend fun insertPokemon(
        pokemonInfo: PokemonInfo
    ) = service.insertPokemon(pokemonInfo)

    /** 특성 추가 **/
    suspend fun insertCharacteristic(
        characteristicInfo: CharacteristicInfo
    ) = service.insertCharacteristic(characteristicInfo)
}