package com.example.network.repository

import com.example.network.model.PokemonInfo
import kotlinx.coroutines.flow.Flow


interface ExternalRepository {

    /** 포켓몬 정보 조회 **/
    fun pokemonInfo(index: Int): Flow<PokemonInfo>

}