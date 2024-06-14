package com.example.mjapp.ui.screen.other.english_words.exam

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.other.english_words.ExamState
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.update
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

    private val _state = mutableStateOf(ExamState())
    val state: State<ExamState> = _state

    init {
        savedStateHandle.get<Int>(Constants.Day)?.let { day ->
            _state.update { it.copy(day = day) }
        }
        fetchExamination()
    }

    private fun fetchExamination() {
        repository
            .fetchExamination(_state.value.day)
            .onStart { startLoading() }
            .onEach { newList -> _state.update { it.copy(list = newList) } }
            .onCompletion { endLoading() }
            .catch { updateNetworkErrorState(true) }
            .launchIn(viewModelScope)
    }

    fun updateMeaning(index: Int, value: String) {
        val updatedList = _state.value.list.toMutableList()
        runCatching {
            updatedList[index] = updatedList[index].copy(meaning = value)
            _state.update { it.copy(list = updatedList) }
        }
    }

    fun examinationScoring(
        onResult: () -> Unit
    ) {
        repository
            .fetchExaminationScoring(_state.value.list)
            .onStart { startLoading() }
            .onEach { result ->
                onResult()
                _state.update { it.copy(result = result) }
            }
            .onCompletion { endLoading() }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateDay(day: Int) {
        _state.update { it.copy(day = day) }
        fetchExamination()
    }
}