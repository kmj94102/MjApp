package com.example.network.repository

import com.example.network.model.Note
import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
import com.example.network.model.Word
import com.example.network.model.WrongAnswerInsertParam
import com.example.network.model.getFailureThrow
import com.example.network.service.VocabularyClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VocabularyRepositoryImpl @Inject constructor(
    private val client: VocabularyClient
) : VocabularyRepository {
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

    /** 오답 등록 **/
    override fun insertWrongAnswer(param: List<WrongAnswerInsertParam>) = flow{
        client
            .insertWrongAnswer(param)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }
}