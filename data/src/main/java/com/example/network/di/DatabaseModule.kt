package com.example.network.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.network.database.MjDatabase
import com.example.network.database.dao.ElswordDao
import com.example.network.database.dao.InternetDao
import com.example.network.database.dao.PersonaDao
import com.example.network.database.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): MjDatabase {
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                        CREATE TABLE Persona3Quest (
                            id INTEGER NOT NULL,
                            title TEXT NOT NULL,
                            deadline TEXT NOT NULL,
                            condition TEXT NOT NULL,
                            contents TEXT NOT NULL,
                            guide TEXT NOT NULL,
                            reward TEXT NOT NULL,
                            isComplete INTEGER NOT NULL DEFAULT 0
                            PRIMARY KEY(id)
                        )
                    """.trimIndent()
                )
                database.execSQL(
                    """
                        CREATE TABLE Persona3CommunitySelect (
                            id INTEGER NOT NULL,
                            rank INTEGER NOT NULL,
                            contents TEXT NOT NULL,
                            PRIMARY KEY(id)
                        )
                    """.trimIndent()
                )
            }
        }
        return Room
            .databaseBuilder(application, MjDatabase::class.java, "mj_database.db")
            .addMigrations(MIGRATION_4_5)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(
        database: MjDatabase
    ): PokemonDao = database.pokemonDao()

    @Provides
    @Singleton
    fun provideElswordDao(
        database: MjDatabase
    ): ElswordDao = database.elswordDao()

    @Provides
    @Singleton
    fun provideInternetDao(
        database: MjDatabase
    ): InternetDao = database.internetDao()

    @Provides
    @Singleton
    fun providePersonaDao(
        database: MjDatabase
    ): PersonaDao = database.insertPersonaDao()

}