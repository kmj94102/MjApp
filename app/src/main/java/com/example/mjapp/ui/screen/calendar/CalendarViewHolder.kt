package com.example.mjapp.ui.screen.calendar

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.util.getToday
import com.example.network.model.CalendarItem
import com.example.network.model.fetchCalendarInfo
import com.example.network.repository.ExternalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CalendarViewHolder @Inject constructor(
    private val externalRepository: ExternalRepository
) : ViewModel() {

    private val _today = getToday("yyyy.MM.dd")

    private val _year = mutableStateOf(_today.substring(0, 4))
    val year: State<String> = _year

    private val _month = mutableStateOf(_today.substring(5, 7))
    val month: State<String> = _month

    private val _selectDate = mutableStateOf(_today)
    val selectDate: State<String> = _selectDate

    private val _calendarItemList = mutableStateListOf<CalendarItem>()
    val calendarItemList: List<CalendarItem> = _calendarItemList

    val selectItem
        get() = run { _calendarItemList.find { it.detailDate == _selectDate.value } }

    init {
        fetchCalendar()
    }

    fun getToday(): String = _today

    fun updateSelectDate(date: String) {
        _selectDate.value = date
    }

    fun updateYearMonth(year: String, month: String) {
        _year.value = year
        _month.value = month
        _selectDate.value = "$year.$month.01"
        fetchCalendar()
    }

    private fun fetchCalendar() {
        _calendarItemList.clear()
        _calendarItemList.addAll(
            fetchCalendarInfo(_year.value.toInt(), month = _month.value.toInt())
        )
        fetchHolidays()
    }

    private fun fetchHolidays() {
        externalRepository
            .fetchHolidays(year = _year.value, month = _month.value)
            .onEach {
                it.forEach { item ->
                    val index = _calendarItemList.indexOfFirst { calendarItem ->
                        calendarItem.detailDate == item.date
                    }

                    if (index != -1) {
                        _calendarItemList[index] = _calendarItemList[index].copy(
                            isHoliday = item.isDayOff,
                            dateInfo = item.title
                        )
                    }
                }
            }
            .catch {
                Log.e("CalendarViewModel", "fetchHolidays Error : ${it.message}")
            }
            .launchIn(viewModelScope)
    }

}