package com.example.mjapp.ui.screen.game.elsword.counter.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterAddViewModel @Inject constructor(
    private val repository: ElswordRepository
): ViewModel() {

    val list = repository.fetchCounterList()

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _maxCount = mutableStateOf(0)
    val maxCount: State<Int> = _maxCount

    fun updateName(value: String) {
        _name.value = value
    }

    fun updateMaxCount(value: Int) {
        _maxCount.value = value
    }

    fun insertCounter() = viewModelScope.launch {
        repository.insertCounter(
            name = _name.value,
            max = _maxCount.value
        )

        _name.value = ""
        _maxCount.value = 0
    }

    fun deleteCounter(id: Int) = viewModelScope.launch {
        repository.deleteCounter(id)
    }

}