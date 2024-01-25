package com.example.network.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonCounterEntity(
    @PrimaryKey(autoGenerate = true) val index: Int = 0,
    val number: String,
    val name: String,
    val image: String,
    val shinyImage: String,
    val count: Int,
    val customIncrease: Int = 10,
    val priority: Int = 0,
    val isCatch: Boolean = false,
    val timestamp: Long
)