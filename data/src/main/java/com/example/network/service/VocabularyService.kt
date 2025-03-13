package com.example.network.service

import com.example.network.model.Note
import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
import com.example.network.model.Word
import com.example.network.model.WrongAnswerInsertParam
import retrofit2.http.Body
import retrofit2.http.POST

interface VocabularyService {
    /** 노트 조회 **/
    @POST("/vocabulary/select/note")
    suspend fun fetchNotes(@Body item: NoteParam): List<Note>

    /** 단어 조회 **/
    @POST("/vocabulary/select/word")
    suspend fun fetchWords(@Body item: NoteIdParam): List<Word>

    /** 오답 등록 **/
    @POST("/vocabulary/insert/wrongAnswer")
    suspend fun insertWrongAnswer(@Body item: List<WrongAnswerInsertParam>): String

}