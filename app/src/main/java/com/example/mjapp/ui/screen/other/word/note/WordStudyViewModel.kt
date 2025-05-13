package com.example.mjapp.ui.screen.other.word.note

import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getWordStudyCalendar
import com.example.network.model.NetworkError
import com.example.network.model.NoteParam
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WordStudyViewModel @Inject constructor(
    private val repository: VocabularyRepository,
): BaseViewModel() {
    val selectDate = MutableStateFlow(Calendar.getInstance())

    @OptIn(ExperimentalCoroutinesApi::class)
    val list: StateFlow<List<WordStudyCalendar>> = selectDate
        .flatMapLatest {
            repository.fetchNotes(
                NoteParam(
                    year = selectDate.value.get(Calendar.YEAR),
                    month = selectDate.value.get(Calendar.MONTH) + 1,
                    language = "all"
                )
            ).map { list ->
                getWordStudyCalendar(
                    year = selectDate.value.get(Calendar.YEAR),
                    month = selectDate.value.get(Calendar.MONTH) + 1,
                    list = list
                )
            }
        }
        .catch {
            if (it is NetworkError) {
                updateNetworkErrorState()
            } else {
                updateMessage(it.message ?: "??")
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getYearMonth() =
        "${selectDate.value.get(Calendar.YEAR)}년 ${selectDate.value.get(Calendar.MONTH) + 1}월"

    fun prevMonth() {
        selectDate.update {
            val clone = it.clone() as Calendar
            clone.add(Calendar.MONTH, -1)
            clone.set(Calendar.DAY_OF_MONTH, 1)
            clone
        }
    }

    fun nextMonth() {
        selectDate.update {
            val clone = it.clone() as Calendar
            clone.add(Calendar.MONTH, 1)
            clone.set(Calendar.DAY_OF_MONTH, 1)
            clone
        }
    }

    fun updateSelectDate(date: Calendar) {
        selectDate.update { date }
    }

}