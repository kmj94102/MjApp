package com.example.network.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.network.database.entity.ElswordCounterEntity
import com.example.network.database.entity.ElswordProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ElswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounter(elswordCounterEntity: ElswordCounterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(elswordProgressEntity: ElswordProgressEntity)

    @Query("SELECT name FROM ElswordCounterEntity")
    fun fetchCounterTitleList(): Flow<List<String>>

    @Query("SELECT * FROM ElswordCounterEntity")
    fun fetchCounterList(): Flow<List<ElswordCounterEntity>>

    @Query("SELECT * FROM ElswordCounterEntity WHERE id = :id")
    suspend fun fetchCounter(id: Int): ElswordCounterEntity

    @Query("DELETE FROM ElswordCounterEntity WHERE id = :id")
    suspend fun deleteCounter(id: Int)

}