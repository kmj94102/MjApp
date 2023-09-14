package com.example.network.repository

import com.example.network.database.entity.InternetEntity
import com.example.network.model.InternetFavorite
import kotlinx.coroutines.flow.Flow

interface InternetRepository {

    suspend fun insertFavorite(internetEntity: InternetEntity): Result<Unit>

    fun fetchFavorites(): Flow<List<InternetFavorite>>

}