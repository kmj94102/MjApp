package com.example.network.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.network.model.Elsword

@Entity
data class ElswordCounterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val max: Int,
    val complete: String
) {
    fun getProgress(): Int {
        val counter = complete.split(",").size.toDouble()

        return (counter / max * 100).toInt()
    }

    fun completeMap(): Map<String, Boolean> {
        val data = complete.split(",")
        val characterList = Elsword.values().map { it.originName }

        return data.associateWith { characterList.contains(it) }
    }
}
