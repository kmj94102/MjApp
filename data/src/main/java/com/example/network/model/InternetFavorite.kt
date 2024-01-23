package com.example.network.model

import com.example.network.database.entity.InternetEntity

data class InternetFavorite(
    val id: Int,
    val name: String,
    val address: String
) {
    fun toEntity() = InternetEntity(
        id = id,
        name = name,
        address = address
    )

    companion object {
        fun crate() = InternetFavorite(
            id = 0,
            name = "",
            address = ""
        )
    }
}