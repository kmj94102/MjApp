package com.example.mjapp.ui.screen.other.word.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.NetworkError
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: VocabularyRepository,
): BaseViewModel() {

    private val _state = mutableStateOf(NoteState.init(Date()))
    val state: State<NoteState> = _state

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        repository
            .fetchNotes(state.value.toNoteParam())
            .setLoadingState()
            .onEach { list ->
                _state.update { it.copy(noteList = list) }
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

    fun updateSelectLanguage(language: String) {
        _state.update { it.copy(language = language) }
        fetchNotes()
    }

}