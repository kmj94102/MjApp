package com.example.mjapp.ui.screen.other.english_words.memorize

import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.VocabularyListResult
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _day = mutableIntStateOf(1)
    val day: IntState = _day

    private val _list = mutableStateListOf<VocabularyListResult>()
    val list: List<VocabularyListResult> = _list

    val address
        get() = "https://mp3.englishbus.co.kr/KST_VOCA/MP3/1/Day_${
            _day.intValue.toString().padStart(2, '0')
        }.mp3"

    init {
        savedStateHandle.get<Int>(Constants.Day)?.let {
            _day.intValue = it
        }
        fetchVocabularyList()
    }

    private fun fetchVocabularyList() {
        repository
            .fetchVocabulary(_day.intValue)
            .onStart { startLoading() }
            .onEach {
                _list.clearAndAddAll(it)
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

}