package com.example.network.service

import com.example.network.model.DayParam
import com.example.network.model.Examination
import com.example.network.model.ExaminationScoringResult
import com.example.network.model.Note
import com.example.network.model.NoteIdParam
import com.example.network.model.NoteParam
import com.example.network.model.VocabularyList
import com.example.network.model.Word
import com.example.network.model.WrongAnswer
import retrofit2.http.Body
import retrofit2.http.POST

interface VocabularyService {

    @POST("/vocabulary/select")
    suspend fun fetchVocabularyList(@Body item: DayParam): VocabularyList

    @POST("/vocabulary/select/examination")
    suspend fun fetchExamination(@Body item: DayParam): List<Examination>

    @POST("/vocabulary/select/examination/scoring")
    suspend fun fetchExaminationScoring(@Body items: List<Examination>): ExaminationScoringResult

    @POST("/vocabulary/select/wrongAnswer")
    suspend fun fetchWrongAnswer(@Body items: DayParam): List<WrongAnswer>

    @POST("/vocabulary/select/note")
    suspend fun fetchNotes(@Body item: NoteParam): List<Note>

    @POST("/vocabulary/select/word")
    suspend fun fetchWords(@Body item: NoteIdParam): List<Word>

}