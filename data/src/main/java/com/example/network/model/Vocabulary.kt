package com.example.network.model

import java.util.Locale

data class Note(
    val noteId: Int,
    val timestamp: String,
    val title: String,
    val language: String
) {
    fun getLanguageKr() =
        if (language == "us") "영어" else "일어"
}

data class NoteParam(
    val year: Int,
    val month: Int,
    val language: String
)

data class NoteIdParam(
    val idx: Int
)

data class Word(
    val noteId: Int,
    val wordId: Int,
    val word: String,
    val meaning: String,
    val note1: String,
    val note2: String,
    val examples: List<WordExample>
) {
    fun toWordTest() = WordTest(
        noteId = noteId,
        wordId = wordId,
        word = word,
        meaning = meaning,
        examples = examples,
        myAnswer = "",
        hint = examples.randomOrNull()?.hint ?: ""
    )
}

data class WordExample(
    val wordId: Int,
    val wordExampleId: Int,
    val example: String,
    val meaning: String,
    val hint: String,
    val isCheck: Boolean
)

data class WordTest(
    val noteId: Int,
    val wordId: Int,
    val word: String,
    val meaning: String,
    val examples: List<WordExample>,
    val myAnswer: String,
    val hint: String
) {
    fun getNewHint() = this.copy(
        hint = examples.randomOrNull()?.hint ?: ""
    )

    fun updateMyAnswer(value: String) = this.copy(
        myAnswer = value
    )
}

fun List<WordTest>.toWordTestResult(): WordTestResult {
    val correctCount = this.count { it.word == it.myAnswer }
    val score = 100f / size * correctCount

    return WordTestResult(
        totalSize = size,
        correctCount = correctCount,
        score = if(score % 1 == 0.0f) {
            String.format(locale = Locale.ROOT, format = "%.0f점", score)
        } else {
            String.format(locale = Locale.ROOT, format = "%.2f점", score)
        },
        wrongAnswerParams = this.filter { it.word != it.myAnswer }.map {
            WrongAnswerInsertParam(noteIdx = it.noteId, wordIdx = it.wordId)
        }
    )
}

data class WrongAnswerInsertParam(
    val wordIdx: Int,
    val noteIdx: Int
)

data class WordTestResult(
    val totalSize: Int = 0,
    val correctCount: Int = 0,
    val score: String = "",
    val wrongAnswerParams: List<WrongAnswerInsertParam> = listOf()
) {
    fun getCount() = " ($correctCount/${totalSize})"
}