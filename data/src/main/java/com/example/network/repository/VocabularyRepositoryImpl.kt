package com.example.network.repository

import com.example.network.model.DayParam
import com.example.network.model.Examination
import com.example.network.model.getFailureThrow
import com.example.network.service.VocabularyClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VocabularyRepositoryImpl @Inject constructor(
    private val client: VocabularyClient
) : VocabularyRepository {
    override fun fetchVocabulary(day: Int) = flow {
        client
            .fetchVocabularyList(DayParam(day))
            .onSuccess {
                emit(it.result)
            }
            .getFailureThrow()
    }

    override fun fetchExamination(day: Int) = flow {
        client
            .fetchExamination(DayParam(day))
            .onSuccess {
                emit(
                    it.filter { item -> item.word.isNotEmpty() }
                        .map { item -> item.copy(meaning = "") }
                )
            }
            .getFailureThrow()
    }

    override fun fetchExaminationScoring(items: List<Examination>) = flow {
        client
            .fetchExaminationScoring(items)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    override fun fetchWrongAnswer(day: Int) = flow {
        client
            .fetchWrongAnswer(DayParam(day))
            .onSuccess { emit(it) }
            .getFailureThrow()
    }
}