package com.example.network.repository

import com.example.network.model.ElswordQuest
import com.example.network.model.ElswordQuestSimple

interface ElswordRepository {
    suspend fun insertQuest(quest: ElswordQuest): String

    suspend fun deleteQuest(id: Int)

    suspend fun fetchQuestList(): List<ElswordQuestSimple>
}