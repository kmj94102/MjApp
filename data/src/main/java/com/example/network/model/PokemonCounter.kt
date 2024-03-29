package com.example.network.model

data class PokemonCounter(
    val index: Int,
    val name: String,
    val number: String,
    val image: String,
    val shinyImage: String,
    val count: Int,
    val customIncrease: Int = 10,
) {
    companion object {
        fun init() = PokemonCounter(
            index = 0,
            name = "",
            number = "",
            image = "",
            shinyImage = "",
            count = 0
        )
    }
}