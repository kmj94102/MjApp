package com.example.network.repository

import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

interface ElswordRepository {
    suspend fun insertQuest(quest: ElswordQuest): String

    suspend fun deleteQuest(id: Int)

    suspend fun fetchQuestList(): List<ElswordQuestSimple>

    suspend fun fetchQuestDetailList(): List<ElswordQuestDetail>

    suspend fun updateQuest(item: ElswordQuestUpdate)

    fun fetchQuestCounter(): Flow<List<ElswordCounter>>

    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int

}