package com.example.network.repository

import com.example.network.model.PokemonInfo
import kotlinx.coroutines.flow.Flow


interface ExternalRepository {

    fun pokemonInfo(index: Int): Flow<PokemonInfo>

}