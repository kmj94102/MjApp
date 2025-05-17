package com.example.mjapp.ui.screen.other.word.wronganswer

import com.example.network.model.WrongAnswerItem
import com.example.network.model.WrongAnswerParam

data class WrongAnswerState(
    val list: List<WrongAnswerItem> = emptyList(),
    val totalCount: Int = 0,
    val sort: String = WrongAnswerParam.SORT_TIMESTAMP,
)