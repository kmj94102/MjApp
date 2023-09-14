package com.example.network.service

import com.example.network.database.dao.InternetDao
import com.example.network.database.entity.InternetEntity
import javax.inject.Inject

class InternetClient @Inject constructor(
    private val dao: InternetDao
){

    suspend fun insertFavorite(
        internetEntity: InternetEntity
    ) = runCatching { dao.insertInternetFavorite(internetEntity) }

    fun fetchFavorites() = dao.fetchInternetFavorites()

}