package com.example.network.repository

import com.example.network.model.Note
import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
import com.example.network.model.Word
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    /** 노트 조회 **/
    fun fetchNotes(param: NoteParam): Flow<List<Note>>

    /** 단어 조회 **/
    fun fetchWords(param: NoteIdParam): Flow<List<Word>>
}