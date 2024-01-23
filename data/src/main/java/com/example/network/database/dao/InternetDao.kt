package com.example.network.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.network.database.entity.InternetEntity
import com.example.network.model.InternetFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface InternetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInternetFavorite(internetEntity: InternetEntity)

    @Query("SELECT * FROM InternetEntity")
    fun fetchInternetFavorites(): Flow<List<InternetFavorite>>

    @Query("DELETE FROM InternetEntity WHERE id=:id")
    suspend fun deleteItem(id: Int)

}