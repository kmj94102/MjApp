package com.example.network.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.network.database.dao.PokemonDao
import com.example.network.database.entity.PokemonCounterEntity

@Database(
    entities = [PokemonCounterEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MjDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}