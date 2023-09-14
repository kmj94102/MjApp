package com.example.network.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InternetEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String
)