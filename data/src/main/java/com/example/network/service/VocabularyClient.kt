package com.example.network.service

import com.example.network.model.DayParam
import javax.inject.Inject

class VocabularyClient @Inject constructor(
    private val service: VocabularyService
) {
    suspend fun fetchVocabularyList(item: DayParam) = runCatching {
        service.fetchVocabularyList(item)
    }
}