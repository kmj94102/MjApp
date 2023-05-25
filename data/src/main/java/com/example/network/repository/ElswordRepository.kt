package com.example.network.repository

import com.example.network.model.ElswordQuest
import com.example.network.model.ElswordQuestDetail
import com.example.network.model.ElswordQuestSimple
import com.example.network.model.ElswordQuestUpdate

interface ElswordRepository {
    suspend fun insertQuest(quest: ElswordQuest): String

    suspend fun deleteQuest(id: Int)

    suspend fun fetchQuestList(): List<ElswordQuestSimple>

    suspend fun fetchQuestDetailList(): List<ElswordQuestDetail>

    suspend fun updateQuest(item: ElswordQuestUpdate)

}