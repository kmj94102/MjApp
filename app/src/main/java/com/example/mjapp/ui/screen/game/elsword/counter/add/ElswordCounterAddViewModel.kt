package com.example.mjapp.ui.screen.game.elsword.counter.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.ElswordQuest
import com.example.network.model.ElswordQuestSimple
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterAddViewModel @Inject constructor(
    private val repository: ElswordRepository
) : BaseViewModel() {

    private val _list = mutableStateListOf<ElswordQuestSimple>()
    val list: List<ElswordQuestSimple> = _list

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _maxCount = mutableIntStateOf(0)
    val maxCount: State<Int> = _maxCount

    init {
        fetchQuestList()
    }

    private fun fetchQuestList() = viewModelScope.launch {
        _list.clear()
        _list.addAll(repository.fetchQuestList())
    }

    fun updateName(value: String) {
        _name.value = value
    }

    fun updateMaxCount(value: String) {
        _maxCount.intValue =
            runCatching { value.toInt() }.getOrElse { if (value.isEmpty()) 0 else _maxCount.intValue }
    }

    fun insertCounter() = viewModelScope.launch {
        val result = repository.insertQuest(
            ElswordQuest(name = _name.value, max = _maxCount.intValue)
        )
        updateMessage(result)

        _name.value = ""
        _maxCount.intValue = 0
        fetchQuestList()
    }

    fun deleteCounter(id: Int) = viewModelScope.launch {
        repository.deleteQuest(id)
        fetchQuestList()
    }

}