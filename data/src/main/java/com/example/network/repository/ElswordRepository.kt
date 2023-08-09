package com.example.network.repository

import com.example.network.model.*

interface ElswordRepository {
    /** 퀘스트 등록 */
    suspend fun insertQuest(quest: ElswordQuest): String

    /** 퀘스트 삭제 **/
    suspend fun deleteQuest(id: Int)

    /** 퀘스트 조회 **/
    suspend fun fetchQuestList(): List<ElswordQuestSimple>

    /** 퀘스트 상세 조회 **/
    suspend fun fetchQuestDetailList(): List<ElswordQuestDetail>

    /** 퀘스트 업데이트 **/
    suspend fun updateQuest(item: ElswordQuestUpdate)

    /** 퀘스트 카운터 업데이트 **/
    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int

}