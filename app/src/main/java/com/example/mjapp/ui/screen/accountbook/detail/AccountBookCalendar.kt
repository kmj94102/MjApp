package com.example.mjapp.ui.screen.accountbook.detail

import com.example.network.model.AccountBookItem
import com.example.network.model.CalendarItem
import com.example.network.model.MyCalendar
import java.text.SimpleDateFormat
import java.util.*

fun createAccountBookCalendarList(
    startDate: String,
    endDate: String,
    list: List<AccountBookItem>
): List<AccountBookCalendar> {
    val sdfInput = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val sdfOutput = SimpleDateFormat("dd", Locale.getDefault())

    val startCalendar = Calendar.getInstance()
    val endCalendar = Calendar.getInstance()

    runCatching {
        startCalendar.time = sdfInput.parse(startDate) ?: return emptyList()
        endCalendar.time = sdfInput.parse(endDate) ?: return emptyList()
    }.onFailure {
        return emptyList()
    }

    val calendarList = mutableListOf<AccountBookCalendar>()

    val dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK)
    val emptyDays = if (dayOfWeek == Calendar.SUNDAY) 0 else dayOfWeek - 1

    repeat(emptyDays) {
        calendarList.add(AccountBookCalendar())
    }

    while (startCalendar <= endCalendar) {
        val date = sdfOutput.format(startCalendar.time)
        val todayOfWeek = getDayOfWeek(startCalendar.get(Calendar.DAY_OF_WEEK))
        val detailDate = getDetailDate(
            startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH) + 1,
            startCalendar.get(Calendar.DAY_OF_MONTH).toString()
        )
        val itemList = list.filter { it.date == detailDate }

        val myCalendar = AccountBookCalendar(
            date = date,
            dayOfWeek = todayOfWeek,
            detailDate = detailDate,
            itemList = itemList
        )


        calendarList.add(myCalendar)

        startCalendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return calendarList
}


private fun getDayOfWeek(index: Int): String {
    val list = listOf("일", "월", "화", "수", "목", "금", "토")
    return list[index - 1]
}

private fun getDetailDate(year: Int, month: Int, date: String) =
    "$year.${month.toString().padStart(2, '0')}.${date.padStart(2, '0')}"

data class AccountBookCalendar(
    val date: String = "",
    val dayOfWeek: String = "",
    val detailDate: String = "",
    val itemList: List<AccountBookItem> = mutableListOf(),
)