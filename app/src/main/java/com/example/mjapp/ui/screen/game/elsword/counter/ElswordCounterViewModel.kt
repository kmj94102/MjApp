package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.ElswordQuestDetail
import com.example.network.model.ElswordQuestUpdate
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterViewModel @Inject constructor(
    private val repository: ElswordRepository
) : ViewModel() {

    private val _selectCounter = mutableStateOf(0)
    val selectCounter: State<Int> = _selectCounter

    private val _list = mutableStateListOf<ElswordQuestDetail>()
    val list: List<ElswordQuestDetail> = _list

    init {
        fetchQuestDetail()
    }

    private fun fetchQuestDetail() = viewModelScope.launch {
        _list.clear()
        _list.addAll(repository.fetchQuestDetailList())
    }

    fun updateQuest(update: ElswordQuestUpdate) = viewModelScope.launch {
        repository.updateQuest(update)
        fetchQuestDetail()
    }

    fun chaneSelector(value: String) {
        val index = _list.indexOfFirst { it.name == value }
        if (index != -1) {
            _selectCounter.value = index
        }
    }

}