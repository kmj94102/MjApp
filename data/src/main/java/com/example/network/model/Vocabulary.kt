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