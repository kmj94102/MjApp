package com.example.network.service

import com.example.network.model.DayParam
import com.example.network.model.Examination
import javax.inject.Inject

class VocabularyClient @Inject constructor(
    private val service: VocabularyService
) {
    suspend fun fetchVocabularyList(item: DayParam) = runCatching {
        service.fetchVocabularyList(item)
    }

    suspend fun fetchExamination(item: DayParam) = runCatching {
        service.fetchExamination(item)
    }

    suspend fun fetchExaminationScoring(items: List<Examination>) = runCatching {
        service.fetchExaminationScoring(items)
    }
}