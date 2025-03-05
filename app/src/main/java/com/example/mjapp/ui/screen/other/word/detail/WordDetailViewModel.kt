package com.example.mjapp.ui.screen.other.word.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.update
import com.example.network.model.NetworkError
import com.example.network.model.NoteIdParam
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val repository: VocabularyRepository,
    val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _state = mutableStateOf(
        WordDetailState(
            idx = savedStateHandle.get<Int>(Constants.INDEX) ?: 0,
            title = savedStateHandle.get<String>(Constants.TITLE) ?: ""
        )
    )
    val state: State<WordDetailState> = _state

    init {
        fetchWords()
    }

    private fun fetchWords() {
        repository
            .fetchWords(NoteIdParam(state.value.idx))
            .setLoadingState()
            .onEach { list ->
                _state.update { it.copy(list = list) }
            }
            .catch {
                if (it is NetworkError) {
                    updateNetworkErrorState()
                } else {
                    updateMessage(it.message ?: "??")
                }
            }
            .launchIn(viewModelScope)
    }

}