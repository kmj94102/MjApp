package com.example.mjapp.ui.screen.other.word.detail

import com.example.network.model.Word

data class WordDetailState(
    val idx: Int,
    val title: String,
    val list: List<Word> = listOf()
)
