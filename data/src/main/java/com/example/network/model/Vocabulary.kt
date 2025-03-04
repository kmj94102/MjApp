package com.example.network.model

data class DayParam(
    val day: Int
)

data class VocabularyList(
    val result: List<VocabularyListResult>
)

data class VocabularyListResult(
    val wordGroup: Word,
    val list: List<Vocabulary>
)

data class Word(
    val modify: String,
    val day: Int,
    val name: String,
    val meaning: String,
    val id: Int
)

data class Vocabulary(
    val meaning: String,
    val day: Int,
    val group: String,
    val hint: String,
    val id: Int,
    val word: String
)

data class Examination(
    val id: Int,
    val word: String,
    val meaning: String = ""
)

data class ExaminationScoringResult(
    val totalSize: Int = 0,
    val correctCount: Int = 0,
    val wrongItems: List<Vocabulary> = listOf()
) {
    fun getScore(): String {
        val score = 100f / totalSize * correctCount

        return if(score % 1 == 0.0f) {
            String.format("%.0f점", score)
        } else {
            String.format("%.2f점", score)
        }
    }

    fun getCount() = " ($correctCount/${totalSize})"
}

data class WrongAnswer(
    val id: Int,
    val day: Int,
    val word: String,
    val meaning: String,
    val hint: String,
    val count: Int
)


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