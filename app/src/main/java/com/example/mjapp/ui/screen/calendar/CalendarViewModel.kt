package com.example.mjapp.ui.screen.calendar

import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.NetworkError
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: CalendarRepository
) : BaseViewModel() {
    private val _selectDate = MutableStateFlow(Calendar.getInstance())
    val selectDate: StateFlow<Calendar> = _selectDate

    fun getYearMonth() =
        "${selectDate.value.get(Calendar.YEAR)}년 ${selectDate.value.get(Calendar.MONTH) + 1}월"

    @OptIn(ExperimentalCoroutinesApi::class)
    val calendarInfo = _selectDate
        .flatMapLatest {
            repository.fetchCalendarByMonth(
                year = selectDate.value.get(Calendar.YEAR),
                month = selectDate.value.get(Calendar.MONTH) + 1,
            )
        }
        .catch {
            if (it is NetworkError) {
                updateNetworkErrorState()
            } else {
                updateMessage(it.message ?: "??")
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun prevMonth() {
        _selectDate.update {
            val clone = it.clone() as Calendar
            clone.add(Calendar.MONTH, -1)
            clone.set(Calendar.DAY_OF_MONTH, 1)
            clone
        }
    }

    fun nextMonth() {
        _selectDate.update {
            val clone = it.clone() as Calendar
            clone.add(Calendar.MONTH, 1)
            clone.set(Calendar.DAY_OF_MONTH, 1)
            clone
        }
    }

    fun updateSelectDate(date: Calendar) {
        _selectDate.update { date }
    }

}