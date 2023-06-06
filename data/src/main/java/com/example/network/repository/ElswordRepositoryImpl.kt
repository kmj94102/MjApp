package com.example.network.repository

import com.example.network.model.*
import com.example.network.service.ElswordClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun fetchQuestDetailList(): List<ElswordQuestDetail> {
        return try {
            client.fetchQuestDetailList().mapNotNull {
                it.toElswordQuestDetail()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun updateQuest(item: ElswordQuestUpdate) {
        return try {
            client.updateQuest(item)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun fetchQuestCounter() = flow {
        try {
            emit(
                client.fetchQuestCounter().mapNotNull {
                    it.toElswordCounter()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override suspend fun updateQuestCounter(
        item: ElswordCounterUpdateItem
    ) = try {
        client.updateQuestCounter(item)
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }

}