package com.example.mjapp.ui.screen.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getToday
import com.example.network.model.MyCalendar
import com.example.network.model.MyCalendarInfo
import com.example.network.model.NetworkError
import com.example.network.model.TaskUpdateItem
import com.example.network.model.fetchMyCalendarByMonth
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: CalendarRepository
) : BaseViewModel() {

    private val _isCalendar = mutableStateOf(true)
    val isCalendar: State<Boolean> = _isCalendar

    private val _calendarItemList = mutableStateListOf<MyCalendar>()
    val calendarItemList: List<MyCalendar> = _calendarItemList

    val today = getToday("yyyy.MM.dd")

    private val _selectDate = mutableStateOf(today)
    val selectDate: State<String> = _selectDate

    val year get() = run { _selectDate.value.substring(0, 4) }

    val month get() = run { _selectDate.value.substring(5, 7) }

    val selectItem get() = run { _calendarItemList.find { it.detailDate == _selectDate.value } }

    init { fetchCalendar() }

    fun updateSelectDate(date: String) { _selectDate.value = date }

    fun updateYearMonth(year: String, month: String) {
        _selectDate.value = "$year.$month.01"
        fetchCalendar()
    }

    fun updateMode(isCalendar: Boolean) { _isCalendar.value = isCalendar }

    private fun fetchCalendar() {
        val list = fetchMyCalendarByMonth(year = year.toInt(), month = month.toInt())
        _calendarItemList.clear()
        _calendarItemList.addAll(list)
        fetchCalendarItems()
    }

    fun fetchCalendarItems() {
        repository
            .fetchCalendarByMonth(
                year = year.toInt(),
                month = month.toInt()
            )
            .onStart { startLoading() }
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
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
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

    fun deleteSchedule(id: Int) = viewModelScope.launch {
        repository.deleteSchedule(id)
        fetchCalendar()
    }

    fun deletePlanTasks(id: Int) = viewModelScope.launch {
        repository.deletePlanTasks(id)
        fetchCalendar()
    }

    fun updateTask(id: Int, isCompleted: Boolean) = viewModelScope.launch {
        repository
            .updateTask(TaskUpdateItem(id, isCompleted))
            .onSuccess {
                runCatching {
                    selectItem?.itemList
                }
            }
            .onFailure { updateMessage(it.message ?: "오류가 발생하였습니다.") }
    }

}