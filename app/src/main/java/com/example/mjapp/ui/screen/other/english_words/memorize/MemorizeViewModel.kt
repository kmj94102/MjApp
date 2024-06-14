package com.example.mjapp.ui.screen.other.english_words.memorize

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.other.english_words.MemorizeState
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.update
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MemorizeViewModel @Inject constructor(
    private val repository: VocabularyRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = mutableStateOf(MemorizeState())
    val state: State<MemorizeState> = _state

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    init {
        savedStateHandle.get<Int>(Constants.Day)?.let { day ->
            _state.update { it.copy(day = day) }
            _address.value = getAudioAddress(day)
        }
        fetchVocabularyList()
    }

    private fun getAudioAddress(day: Int) =
        "https://mp3.englishbus.co.kr/KST_VOCA/MP3/1/Day_${
            day.toString().padStart(2, '0')
        }.mp3"

    private fun fetchVocabularyList() {
        repository
            .fetchVocabulary(_state.value.day)
            .onStart { startLoading() }
            .onEach { newList ->
                _state.update { it.copy(list = newList) }
            }
            .catch { updateNetworkErrorState(true) }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateDay(day: Int) {
        _state.update { it.copy(day = day) }
        _address.value = getAudioAddress(day)
        fetchVocabularyList()
    }

}