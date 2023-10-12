package com.example.mjapp.ui.screen.other.english_words.exam

import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.Examination
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val repository: VocabularyRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _day = mutableIntStateOf(1)
    val day: IntState = _day

    private val _list = mutableStateListOf<Examination>()
    val list: List<Examination> = _list

    init {
        savedStateHandle.get<Int>(Constants.Day)?.let {
            _day.intValue = it
        }
        fetchExamination()
    }

    private fun fetchExamination() {
        repository
            .fetchExamination(_day.intValue)
            .onStart { startLoading() }
            .onEach { _list.clearAndAddAll(it) }
            .onCompletion { endLoading() }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun updateMeaning(index: Int, value: String) {
        val updatedList = _list.toMutableList()
        runCatching {
            updatedList[index] = updatedList[index].copy(meaning = value)
            _list.clear()
            _list.addAll(updatedList)
        }
    }
}