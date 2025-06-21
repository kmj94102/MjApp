package com.example.network.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.network.database.entity.Persona3Quest
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersona3Quest(list: List<Persona3Quest>)

    @Query("SELECT * FROM Persona3Quest WHERE isComplete = 0")
    fun fetchPersona3Quest(): Flow<List<Persona3Quest>>

    @Query("UPDATE Persona3Quest SET isComplete = :isComplete WHERE id = :id")
    fun updatePersona3Quest(id: Int, isComplete: Boolean)
}