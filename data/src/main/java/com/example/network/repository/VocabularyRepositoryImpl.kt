package com.example.network.repository

import com.example.network.model.DayParam
import com.example.network.model.Examination
import com.example.network.model.Note
import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
import com.example.network.model.Word
import com.example.network.model.getFailureThrow
import com.example.network.service.VocabularyClient
import kotlinx.coroutines.flow.Flow
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


    /** 노트 조회 **/
    override fun fetchNotes(param: NoteParam): Flow<List<Note>> = flow {
        client
            .fetchNotes(param)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 단어 조회 **/
    override fun fetchWords(param: NoteIdParam): Flow<List<Word>> = flow {
        client
            .fetchWords(param)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }
}