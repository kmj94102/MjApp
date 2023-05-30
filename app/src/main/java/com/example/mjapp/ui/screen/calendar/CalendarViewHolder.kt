package com.example.mjapp.ui.screen.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.util.getToday
import com.example.network.model.MyCalendar
import com.example.network.model.MyCalendarInfo
import com.example.network.model.fetchMyCalendarByMonth
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewHolder @Inject constructor(
    private val repository: CalendarRepository
) : ViewModel() {

    private val _today = getToday("yyyy.MM.dd")

    private val _year = mutableStateOf(_today.substring(0, 4))
    val year: State<String> = _year

    private val _month = mutableStateOf(_today.substring(5, 7))
    val month: State<String> = _month

    private val _selectDate = mutableStateOf(_today)
    val selectDate: State<String> = _selectDate

    private val _calendarItemList = mutableStateListOf<MyCalendar>()
    val calendarItemList: List<MyCalendar> = _calendarItemList

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
            fetchMyCalendarByMonth(_year.value.toInt(), month = _month.value.toInt())
        )
        fetchCalendarItems()
    }

    private fun fetchCalendarItems() {
        repository
            .fetchCalendarByMonth(
                year = _year.value.toInt(),
                month = _month.value.toInt()
            )
            .onEach {
                it.forEach { item ->
                    setCalendarItem(item)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchCalendarItem() = viewModelScope.launch {
        val item = repository.fetchCalendarByDate(_selectDate.value)
        if (item == null) {
            setCalendarItemClear(_selectDate.value)
            return@launch
        }

        setCalendarItem(item)
    }

    private fun setCalendarItem(item: MyCalendarInfo) {
        val index = _calendarItemList.indexOfFirst { myCalendar ->
            myCalendar.detailDate == item.date
        }

        if (index != -1) {
            _calendarItemList[index] = _calendarItemList[index].copy(
                isHoliday = item.isHoliday,
                isSpecialDay = item.isSpecialDay,
                dateInfo = item.info,
                itemList = item.list.toMutableList()
            )
        }
    }

    private fun setCalendarItemClear(date: String) {
        val index = _calendarItemList.indexOfFirst { myCalendar ->
            myCalendar.detailDate == date
        }

        if (index != -1) {
            _calendarItemList[index] = _calendarItemList[index].copy(
                isHoliday = false,
                isSpecialDay = false,
                dateInfo = "",
                itemList = mutableListOf()
            )
        }
    }

    fun deleteSchedule(id: Int) = viewModelScope.launch {
        repository.deleteSchedule(id)
        fetchCalendarItem()
    }

    fun deletePlanTasks(id: Int) = viewModelScope.launch {
        repository.deletePlanTasks(id)
        fetchCalendarItem()
    }

}