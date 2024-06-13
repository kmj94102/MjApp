package com.example.mjapp.ui.screen.calendar

import com.example.mjapp.util.getToday
import com.example.network.model.MyCalendar

data class CalendarState(
    val isCalendar: Boolean = true,
    val calendarItemList: List<MyCalendar> = listOf(),
    val today: String = getToday("yyyy.MM.dd"),
    val selectDate: String = today,
) {
    fun getYear() = selectDate.substring(0, 4)
    fun getMonth() = selectDate.substring(5, 7)

    fun getYearInt() = getYear().toInt()

    fun getMonthInt() = getMonth().toInt()

    fun getYearMonth() = "${getYear()}.${getMonth()}"

    fun getSelectItem() = calendarItemList.find { it.detailDate == selectDate }
}

data class ScheduleAddUiState(
    val isDateSelectDialogShow: Boolean = false,
    val isTimeSelectDialogShow: Boolean = false,
    val isRecurrenceSelectDialogShow: Boolean = false,
)