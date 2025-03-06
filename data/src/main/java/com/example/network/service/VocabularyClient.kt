package com.example.network.service

import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
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
}