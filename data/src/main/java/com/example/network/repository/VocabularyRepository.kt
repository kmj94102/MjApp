package com.example.network.repository

import com.example.network.model.VocabularyListResult
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    fun fetchVocabulary(day: Int): Flow<List<VocabularyListResult>>
}