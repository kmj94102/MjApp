package com.example.network.service

import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
import com.example.network.model.WrongAnswerInsertParam
import com.example.network.model.WrongAnswerParam
import javax.inject.Inject

class VocabularyClient @Inject constructor(
    private val service: VocabularyService
) {
    /** 노트 조회 **/
    suspend fun fetchNotes(param: NoteParam) = runCatching {
        service.fetchNotes(param)
    }

    /** 단어 조회 **/
    suspend fun fetchWords(param: NoteIdParam) = runCatching {
        service.fetchWords(param)
    }

    /** 오답 등록 **/
    suspend fun insertWrongAnswer(param: List<WrongAnswerInsertParam>) = runCatching {
        service.insertWrongAnswer(param)
    }

    /** 오답 노트 조회 **/
    suspend fun fetchWrongAnswer(param: WrongAnswerParam) = runCatching {
        service.fetchWrongAnswer(param)
    }
}