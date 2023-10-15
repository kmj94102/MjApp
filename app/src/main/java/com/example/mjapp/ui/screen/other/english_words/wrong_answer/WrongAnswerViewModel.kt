package com.example.mjapp.ui.screen.other.english_words.wrong_answer

import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.WrongAnswer
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

    private val _day = mutableIntStateOf(1)
    val day: IntState = _day

    private val _list = mutableStateListOf<WrongAnswer>()
    val list: List<WrongAnswer> = _list

    init {
        savedStateHandle.get<Int>(Constants.Day)?.let {
            _day.intValue = it
        }
        fetchWrongAnswer()
    }

    private fun fetchWrongAnswer() {
        repository
            .fetchWrongAnswer(_day.intValue)
            .onStart { startLoading() }
            .onEach { _list.clearAndAddAll(it) }
            .catch { updateNetworkErrorState(true) }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateDay(day: Int) {
        _day.intValue = day
        fetchWrongAnswer()
    }

}