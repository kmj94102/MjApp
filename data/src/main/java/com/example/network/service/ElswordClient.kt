package com.example.network.service

import com.example.network.model.ElswordQuest
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

}