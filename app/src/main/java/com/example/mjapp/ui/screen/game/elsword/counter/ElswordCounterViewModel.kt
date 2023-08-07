package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.ElswordQuestDetail
import com.example.network.model.ElswordQuestUpdate
import com.example.network.model.ElswordQuestUpdateInfo
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterViewModel @Inject constructor(
    private val repository: ElswordRepository
) : BaseViewModel() {

    private val _selectCounter = mutableIntStateOf(0)
    val selectCounter: State<Int> = _selectCounter

    private val _list = mutableStateListOf<ElswordQuestDetail>()
    val list: List<ElswordQuestDetail> = _list

    val selectItem
        get() = _list.getOrNull(_selectCounter.intValue)

    private val _dialogItem = mutableStateOf(ElswordQuestUpdateInfo.create())
    val dialogItem: State<ElswordQuestUpdateInfo> = _dialogItem

    init {
        fetchQuestDetail()
    }

    private fun fetchQuestDetail() = viewModelScope.launch {
        _list.clear()
        _list.addAll(repository.fetchQuestDetailList())
    }

    fun updateQuest(
        name: String,
        type: String,
        progress: Int
    ) = viewModelScope.launch {
        val update = ElswordQuestUpdate(
            id = selectItem?.id ?: 0,
            name = name,
            type = type,
            progress = progress
        )
        repository.updateQuest(update)
        listUpdate(update)
    }

    private fun listUpdate(update: ElswordQuestUpdate) {
        val index = _selectCounter.intValue
        if (index >= _list.size) return

        val updatedList = _list.toMutableList()
        val updatedCharacters = updatedList[index].characters.toMutableList()
        val characterIndex = updatedCharacters.indexOfFirst {
            it.name == update.name
        }
        if (characterIndex == -1) return

        updatedCharacters[characterIndex] =
            updatedCharacters[characterIndex].updateCopy(update.type)
                .copy(progress = update.progress)
        updatedList[index] = updatedList[index].copy(characters = updatedCharacters)

        _list.clear()
        _list.addAll(updatedList)
    }

    fun chaneSelector(value: String) {
        val index = _list.indexOfFirst { it.name == value }
        if (index != -1) {
            _selectCounter.intValue = index
        }
    }

    fun setDialogItem(characterName: String) {
        selectItem?.let {
            _dialogItem.value = it.getQuestUpdateInfo(characterName)
        }
    }

}