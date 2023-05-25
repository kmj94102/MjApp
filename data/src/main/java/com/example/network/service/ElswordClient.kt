package com.example.network.service

import com.example.network.model.ElswordQuest
import com.example.network.model.ElswordQuestUpdate
import javax.inject.Inject

class ElswordClient @Inject constructor(
    private val  service: ElswordService
) {

    suspend fun insertQuest(quest: ElswordQuest) =
        service.insertQuest(quest)

    suspend fun deleteQuest(id: Int) =
        service.deleteQuest(id)

    suspend fun fetchQuestList() =
        service.fetchQuestList()

    suspend fun fetchQuestDetailList() =
        service.fetchQuestDetailList()

    suspend fun updateQuest(item: ElswordQuestUpdate) =
        service.updateQuest(item)

}