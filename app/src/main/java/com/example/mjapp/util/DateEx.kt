package com.example.mjapp.util

import com.example.mjapp.ui.screen.other.word.note.WordStudyCalendar
import com.example.network.model.Note
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun createCalendarList(
    startDate: String,
    endDate: String,
): List<Calendar?> {
    val sdfInput = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

    val startCalendar = Calendar.getInstance()
    val endCalendar = Calendar.getInstance()

    runCatching {
        startCalendar.time = sdfInput.parse(startDate) ?: return emptyList()
        endCalendar.time = sdfInput.parse(endDate) ?: return emptyList()
    }.onFailure {
        it.printStackTrace()
        return emptyList()
    }

    val dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK)
    val emptyDays = if (dayOfWeek == Calendar.SUNDAY) 0 else dayOfWeek - 1

    val list = mutableListOf<Calendar?>()

    repeat(emptyDays) {
        list.add(null)
    }

    while (startCalendar <= endCalendar) {
        list.add(startCalendar.clone() as Calendar)
        startCalendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return list
}

fun calendarWeek() = listOf("일", "월", "화", "수", "목", "금", "토")

fun getMonthList(year: Int, month: Int): List<Calendar?> {
    val sdfInput = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDay = calendar.time

    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth)
    val lastDay = calendar.time

    return createCalendarList(sdfInput.format(firstDay), sdfInput.format(lastDay))
}

fun getWordStudyCalendar(
    year: Int,
    month: Int,
    list: List<Note>
): List<WordStudyCalendar> {
    val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateList = getMonthList(year, month)
    val resultList = mutableListOf<WordStudyCalendar>()

    dateList.forEach { date ->
        runCatching {
            val filterList = list.filter { it.timestamp == date?.time?.let { sdfInput.format(it) } }

            when {
                filterList.isNotEmpty() -> {
                    resultList.add(WordStudyCalendar(date = date, noteList = filterList))
                }
                else -> {
                    resultList.add(WordStudyCalendar(date = date))
                }
            }
        }.onFailure {
            resultList.add(WordStudyCalendar(date = date))
        }
    }

    return resultList
}

fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
    return cal1 != null && cal2 != null && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}