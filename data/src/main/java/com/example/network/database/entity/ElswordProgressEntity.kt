package com.example.network.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ElswordCounterEntity::class,
            parentColumns = ["id"],
            childColumns = ["counterId"],
            onDelete = CASCADE
        )
    ]
)
data class ElswordProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "counterId")
    val counterId: Int,
    val progress: Int,
    val max: Int
)