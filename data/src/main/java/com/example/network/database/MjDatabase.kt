package com.example.network.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.network.database.dao.ElswordDao
import com.example.network.database.dao.InternetDao
import com.example.network.database.dao.PokemonDao
import com.example.network.database.entity.ElswordCounterEntity
import com.example.network.database.entity.ElswordProgressEntity
import com.example.network.database.entity.InternetEntity
import com.example.network.database.entity.PokemonCounterEntity

@Database(
    entities = [
        PokemonCounterEntity::class,
        ElswordCounterEntity::class,
        ElswordProgressEntity::class,
        InternetEntity::class
    ],
    version = 4,
    exportSchema = true
)
abstract class MjDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    abstract fun elswordDao(): ElswordDao

    abstract fun internetDao(): InternetDao

    val migration_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE PokemonCounterEntity ADD COLUMN name TEXT")
        }
    }
}