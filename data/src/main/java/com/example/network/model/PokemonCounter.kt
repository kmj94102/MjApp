package com.example.network.model

data class PokemonCounter(
    val index: Int,
    val number: String,
    val image: String,
    val shinyImage: String,
    val count: Int,
    val customIncrease: Int = 10,
)