package com.example.network.repository

import com.example.network.model.*
import com.example.network.service.ElswordClient
import javax.inject.Inject

class ElswordRepositoryImpl @Inject constructor(
    private val client: ElswordClient
) : ElswordRepository {

    /** 퀘스트 등록 **/
    override suspend fun insertQuest(quest: ElswordQuest) =
        client
            .insertQuest(quest)
            .printStackTrace()
            .getOrElse { "퀘스트 등록 실패" }

    /** 퀘스트 삭제 **/
    override suspend fun deleteQuest(id: Int) {
        client
            .deleteQuest(id)
            .printStackTrace()
    }

    /** 퀘스트 조회 **/
    override suspend fun fetchQuestList() =
        client
            .fetchQuestList()
            .printStackTrace()
            .getOrElse { emptyList() }
            .mapNotNull { it.toElswordQuestSimple() }

    /** 퀘스트 상세 조회 **/
    override suspend fun fetchQuestDetailList() =
        client
            .fetchQuestDetailList()
            .printStackTrace()
            .getOrElse { emptyList() }
            .mapNotNull { it.toElswordQuestDetail() }

    /** 퀘스트 업데이트 **/
    override suspend fun updateQuest(item: ElswordQuestUpdate) {
        client
            .updateQuest(item)
            .printStackTrace()
    }

    /** 퀘스트 카운트 업데이트 **/
    override suspend fun updateQuestCounter(item: ElswordCounterUpdateItem) =
        client
            .updateQuestCounter(item)
            .printStackTrace()
            .getOrElse { 0 }

}