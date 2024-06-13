package com.example.mjapp.ui.screen.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.MyCalendarInfo
import com.example.network.model.NetworkError
import com.example.network.model.fetchMyCalendarByMonth
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: CalendarRepository
) : BaseViewModel() {

    private val _state = mutableStateOf(CalendarState())
    val state: State<CalendarState> = _state

    init { fetchCalendar() }

    fun updateSelectDate(date: String) {
        _state.update { it.copy(selectDate = date) }
    }

    fun updateYearMonth(year: String, month: String) {
        _state.update { it.copy(selectDate = "$year.$month.01") }
        fetchCalendar()
    }

    fun updateMode(isCalendar: Boolean) {
        _state.update { it.copy(isCalendar = isCalendar) }
    }

    private fun fetchCalendar() {
        val list = fetchMyCalendarByMonth(
            year = state.value.getYearInt(),
            month = state.value.getMonthInt()
        )
        _state.update { it.copy(calendarItemList = list) }
        fetchCalendarItems()
    }

    fun fetchCalendarItems() {
        repository
            .fetchCalendarByMonth(
                year = state.value.getYearInt(),
                month = state.value.getMonthInt()
            )
            .setLoadingState()
            .onEach {
                it.forEach { item ->
                    setCalendarItem(item)
                }
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

    private fun setCalendarItem(item: MyCalendarInfo) {
        val newList = _state.value.calendarItemList.map {
            if (it.detailDate == item.date) {
                it.copy(
                    isHoliday = item.isHoliday,
                    isSpecialDay = item.isSpecialDay,
                    dateInfo = item.info,
                    itemList = item.list.toMutableList()
                )
            } else {
                it
            }
        }

        _state.update { it.copy(calendarItemList = newList) }
    }

    fun deleteSchedule(id: Int) = viewModelScope.launch {
        repository.deleteSchedule(id)
        fetchCalendar()
    }

    fun deletePlanTasks(id: Int) = viewModelScope.launch {
        repository.deletePlanTasks(id)
        fetchCalendar()
    }

    fun updateTask(id: Int, isCompleted: Boolean) {
        repository
            .updateTask(id, isCompleted, _state.value.selectDate)
            .onEach { setCalendarItem(it) }
            .catch { updateMessage(it.message ?: "오류 발생") }
            .launchIn(viewModelScope)
    }

}