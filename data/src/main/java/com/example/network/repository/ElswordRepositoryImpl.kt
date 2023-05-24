package com.example.network.repository

import com.example.network.model.ElswordQuest
import com.example.network.model.ElswordQuestSimple
import com.example.network.service.ElswordClient
import javax.inject.Inject

class ElswordRepositoryImpl @Inject constructor(
    private val client: ElswordClient
): ElswordRepository {

    override suspend fun insertQuest(quest: ElswordQuest): String {
        return try {
            client.insertQuest(quest)
        } catch (e: Exception) {
            e.printStackTrace()
            "퀘스트 등록 실패"
        }
    }

    override suspend fun deleteQuest(id: Int) {
        return try {
            client.deleteQuest(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun fetchQuestList(): List<ElswordQuestSimple> {
        return try {
            client.fetchQuestList().mapNotNull {
                it.toElswordQuestSimple()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}