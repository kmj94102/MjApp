package com.example.network.model

import com.example.network.database.entity.InternetEntity

data class InternetFavorite(
    val name: String,
    val address: String
) {
    fun toEntity() = InternetEntity(
        id = 0,
        name = name,
        address = address
    )

    companion object {
        fun crate() = InternetFavorite(
            name = "",
            address = ""
        )
    }
}