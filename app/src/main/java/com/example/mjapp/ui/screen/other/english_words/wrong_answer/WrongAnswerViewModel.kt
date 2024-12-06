package com.example.mjapp.ui.screen.other.english_words.wrong_answer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.other.english_words.WrongAnswerState
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
class WrongAnswerViewModel @Inject constructor(
    private val repository: VocabularyRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _state = mutableStateOf(WrongAnswerState())
    val state: State<WrongAnswerState> = _state

    init {
        savedStateHandle.get<Int>(Constants.DAY)?.let { day ->
            _state.update { it.copy(day = day) }
        }
        fetchWrongAnswer()
    }

    private fun fetchWrongAnswer() {
        repository
            .fetchWrongAnswer(_state.value.day)
            .onStart { startLoading() }
            .onEach { newList -> _state.update { it.copy(list = newList) } }
            .catch { updateNetworkErrorState(true) }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateDay(day: Int) {
        _state.update { it.copy(day = day) }
        fetchWrongAnswer()
    }

}