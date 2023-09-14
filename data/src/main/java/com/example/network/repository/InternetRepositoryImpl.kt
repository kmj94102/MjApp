package com.example.network.repository

import com.example.network.database.entity.InternetEntity
import com.example.network.service.InternetClient
import javax.inject.Inject

class InternetRepositoryImpl @Inject constructor(
    private val client: InternetClient
): InternetRepository {
    override suspend fun insertFavorite(internetEntity: InternetEntity) =
        client.insertFavorite(internetEntity)

    override fun fetchFavorites() =
        client.fetchFavorites()
}