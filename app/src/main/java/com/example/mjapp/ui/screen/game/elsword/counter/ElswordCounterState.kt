package com.example.mjapp.ui.screen.game.elsword.counter

import com.example.network.model.ElswordQuestDetail
import com.example.network.model.ElswordQuestSimple
import com.example.network.model.ElswordQuestUpdateInfo

data class ElswordCounterUiState(
    val isStatusChangeShow: Boolean = false,
    val isQuestSelectShow: Boolean = false
)

data class ElswordCounterState(
    val selectCounter: Int = 0,
    val list: List<ElswordQuestDetail> = listOf(),
    val dialogItem: ElswordQuestUpdateInfo = ElswordQuestUpdateInfo.create()
) {
    fun getSelectItem() = list.getOrNull(selectCounter)
}

data class ElswordCounterAddState(
    val list: List<ElswordQuestSimple> = listOf(),
    val name: String = "",
    val maxCount: Int = 0
)