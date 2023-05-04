package com.example.network.model

data class PokemonInfo(
    val number: String = "",
    val name: String = "",
    val status: String = "",
    val classification: String = "",
    val characteristic: String = "",
    val attribute: String = "",
    val image: String = "",
    val shinyImage: String = "",
    val spotlight: String = "",
    val description: String = "",
    val generation: Int = 0
)
