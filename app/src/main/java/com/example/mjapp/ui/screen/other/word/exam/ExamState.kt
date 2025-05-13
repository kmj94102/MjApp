package com.example.mjapp.ui.screen.other.word.exam

import com.example.network.model.WordTest
import com.example.network.model.WordTestResult

data class ExamState(
    val list: List<WordTest> = listOf(),
    val result: WordTestResult = WordTestResult(),
    val isResultShow: Boolean = false
)