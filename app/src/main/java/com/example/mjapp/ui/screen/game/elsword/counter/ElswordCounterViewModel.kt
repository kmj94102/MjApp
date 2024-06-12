package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.clearAndAddAll
import com.example.mjapp.util.update
import com.example.network.model.ElswordQuestUpdate
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterViewModel @Inject constructor(
    private val repository: ElswordRepository
) : BaseViewModel() {

    private val _state = mutableStateOf(ElswordCounterState())
    val state: State<ElswordCounterState> = _state


    init {
        fetchQuestDetail()
    }

    private fun fetchQuestDetail() = viewModelScope.launch {
        val newList = repository.fetchQuestDetailList()
        _state.update { it.copy(list = newList) }
    }

    fun updateQuest(
        name: String,
        type: String,
        progress: Int
    ) = viewModelScope.launch {
        val update = ElswordQuestUpdate(
            id = _state.value.getSelectItem()?.id ?: 0,
            name = name,
            type = type,
            progress = progress
        )
        repository.updateQuest(update)
        listUpdate(update)
    }

    private fun listUpdate(update: ElswordQuestUpdate) {
        val value = _state.value
        val index = value.selectCounter
        if (index >= value.list.size) return

        val updatedList = value.list.toMutableList()
        val updatedCharacters = updatedList[index].characters.toMutableList()
        val characterIndex = updatedCharacters.indexOfFirst {
            it.name == update.name
        }
        if (characterIndex == -1) return

        updatedCharacters[characterIndex] =
            updatedCharacters[characterIndex].updateCopy(update.type)
                .copy(progress = update.progress)
        updatedList[index] = updatedList[index].copy(characters = updatedCharacters)

        _state.update {
            it.copy(list = _state.value.list.clearAndAddAll(updatedList))
        }
    }

    fun chaneSelector(value: String) {
        val index = _state.value.list.indexOfFirst { it.name == value }
        if (index != -1) {
            _state.update { it.copy(selectCounter = index) }
        }
    }

    fun setDialogItem(characterName: String) {
        _state.value.getSelectItem()?.let { item ->
            _state.update {
                it.copy(
                    dialogItem = item.getQuestUpdateInfo(characterName)
                )
            }
        }
    }

}