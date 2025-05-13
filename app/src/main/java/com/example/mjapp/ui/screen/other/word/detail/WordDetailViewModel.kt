package com.example.mjapp.ui.screen.other.word.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.NetworkError
import com.example.network.model.NoteIdParam
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    val repository: VocabularyRepository,
    val savedStateHandle: SavedStateHandle
): BaseViewModel() {
    private val info = savedStateHandle.toRoute<NavScreen2.WordDetail>()
    val list = repository
        .fetchWords(NoteIdParam(info.id))
        .catch {
            if (it is NetworkError) {
                updateNetworkErrorState()
            } else {
                updateMessage(it.message ?: "??")
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getTitle() = info.title
}