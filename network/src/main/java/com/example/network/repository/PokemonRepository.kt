package com.example.network.repository

import com.example.network.model.PokemonInfo

interface PokemonRepository {
    suspend fun insertPokemon(pokemonInfo: PokemonInfo): String
}