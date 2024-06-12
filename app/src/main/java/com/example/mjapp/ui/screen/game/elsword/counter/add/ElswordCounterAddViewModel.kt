package com.example.mjapp.ui.screen.game.elsword.counter.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.game.elsword.counter.ElswordCounterAddState
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.ElswordQuest
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterAddViewModel @Inject constructor(
    private val repository: ElswordRepository
) : BaseViewModel() {

    private val _state = mutableStateOf(ElswordCounterAddState())
    val state: State<ElswordCounterAddState> = _state

    init {
        fetchQuestList()
    }

    private fun fetchQuestList() = viewModelScope.launch {
        _state.value = _state.value.copy(
            list = repository.fetchQuestList()
        )
    }

    fun updateName(value: String) {
        _state.value = _state.value.copy(
            name = value
        )
    }

    fun updateMaxCount(value: String) {
        _state.value = _state.value.copy(
            maxCount = runCatching { value.toInt() }
                .getOrElse { if (value.isEmpty()) 0 else _state.value.maxCount }
        )
    }

    fun insertCounter() = viewModelScope.launch {
        val value = _state.value
        val result = repository.insertQuest(
            ElswordQuest(name = value.name, max = value.maxCount)
        )
        updateMessage(result)

        _state.value = _state.value.copy(name = "", maxCount = 0)
        fetchQuestList()
    }

    fun deleteCounter(id: Int) = viewModelScope.launch {
        repository.deleteQuest(id)
        fetchQuestList()
    }

}