package com.example.network.service

import com.example.network.model.CharacteristicInfo
import com.example.network.model.PokemonInfo
import javax.inject.Inject

class PokemonClient @Inject constructor(
    private val service: PokemonService
) {
    suspend fun insertPokemon(
        pokemonInfo: PokemonInfo
    ) = service.insertPokemon(pokemonInfo)

    suspend fun insertCharacteristic(
        characteristicInfo: CharacteristicInfo
    ) = service.insertCharacteristic(characteristicInfo)
}