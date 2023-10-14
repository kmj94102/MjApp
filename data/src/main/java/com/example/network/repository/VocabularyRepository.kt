package com.example.network.repository

import com.example.network.model.Examination
import com.example.network.model.ExaminationScoringResult
import com.example.network.model.VocabularyListResult
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    fun fetchVocabulary(day: Int): Flow<List<VocabularyListResult>>

    fun fetchExamination(day: Int): Flow<List<Examination>>

    fun fetchExaminationScoring(items: List<Examination>): Flow<ExaminationScoringResult>
}