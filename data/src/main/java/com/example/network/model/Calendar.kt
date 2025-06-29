package com.example.network.model

import java.text.SimpleDateFormat
import java.util.*

/**
 * 달력 아이템
 * @param date 날짜
 * @param isHoliday 공휴일 여부
 * @param isSpecialDay 특별일 여부
 * @param dateInfo 날짜 정보 (ex : 추석, 설날)
 * @param dayOfWeek 요일
 * @param detailDate 상세 날짜
 * @param itemList 일정 리스트
 * **/
data class MyCalendar(
    val date: String = "",
    val isHoliday: Boolean = false,
    val isSpecialDay: Boolean = false,
    val dateInfo: String = "",
    val dayOfWeek: String = "",
    val detailDate: String = "",
    val itemList: MutableList<CalendarItem> = mutableListOf(),
) {
    fun getDateAndDayOfWeek() = "${date.padStart(2, '0')}(${dayOfWeek})"
}

/** 달력 정보 생성 **/
fun fetchMyCalendarByMonth(
    year: Int,
    month: Int
): List<MyCalendar> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDay = calendar.get(Calendar.DAY_OF_WEEK)

    val lastDayIndex = firstDay + monthDays - 2
    val lastIndex = if (lastDayIndex < 35) 34 else 41

    return (0..lastIndex)
        .map {
            if (it < firstDay - 1 || it > lastDayIndex) {
                MyCalendar()
            } else {
                val date = (it - firstDay + 2).toString()
                MyCalendar(
                    date = date,
                    dayOfWeek = getDayOfWeek(it),
                    detailDate = getDetailDate(year, month, date)
                )
            }
        }
}

private fun getDayOfWeek(index: Int): String {
    val list = listOf("일", "월", "화", "수", "목", "금", "토")
    return list[index % 7]
}

private fun getDetailDate(year: Int, month: Int, date: String) =
    "$year.${month.toString().padStart(2, '0')}.${date.padStart(2, '0')}"

data class CalendarResult(
    val date: String,
    val calendarInfoList: List<CalendarInfo>,
    val scheduleInfoList: List<CalendarItem.ScheduleInfo>,
    val planInfoList: List<CalendarItem.PlanInfo>
) {
    fun toMyCalendarInfo(): MyCalendarInfo {
        val isHoliday = calendarInfoList.any { it.isHoliday }
        val isSpecialDay = calendarInfoList.any { it.isSpecialDay }
        val info = if (calendarInfoList.isNotEmpty()) {
            calendarInfoList.map { it.info }.reduce { acc, s -> "$acc, $s" }
        } else ""

        return MyCalendarInfo(
            date = date.replace("-", "."),
            info = info,
            isHoliday = isHoliday,
            isSpecialDay = isSpecialDay,
            list = scheduleInfoList + planInfoList
        )
    }

    fun toScheduleCalendarInfo(): ScheduleCalendarInfo? {
        val isHoliday = calendarInfoList.any { it.isHoliday }
        val isSpecialDay = calendarInfoList.any { it.isSpecialDay }
        val info = if (calendarInfoList.isNotEmpty()) {
            calendarInfoList.map { it.info }.reduce { acc, s -> "$acc, $s" }
        } else ""
        val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = sdfInput.parse(date) ?: return null

        return ScheduleCalendarInfo(
            date = calendar,
            isHoliday = isHoliday,
            isSpecialDay = isSpecialDay,
            dateInfo = info,
            itemList = mutableListOf<CalendarItem>().also {
                it.addAll(scheduleInfoList)
                it.addAll(planInfoList)
            }
        )
    }
}

data class MyCalendarInfo(
    val date: String,
    val info: String,
    val isHoliday: Boolean,
    val isSpecialDay: Boolean,
    val list: List<CalendarItem>
)

data class CalendarInfo(
    val id: Int,
    val info: String,
    val isHoliday: Boolean,
    val isSpecialDay: Boolean
)

sealed class CalendarItem(val type: String) {

    data class ScheduleInfo(
        val id: Int,
        val startTime: String,
        val endTime: String,
        val recurrenceType: String,
        val recurrenceEndDate: String?,
        val scheduleContent: String,
        val scheduleTitle: String,
        val recurrenceId: Int?
    ) : CalendarItem(Schedule) {
        fun getTime() = "${startTime.substring(11, 16)} ~ ${endTime.substring(11, 16)}"
    }

    data class PlanInfo(
        val id: Int,
        val title: String,
        val taskList: List<TaskInfo>
    ) : CalendarItem(Plan)

    data class TaskInfo(
        val id: Int,
        val contents: String,
        val isCompleted: Boolean
    )

    companion object {
        const val Schedule = "schedule"
        const val Plan = "plan"
    }
}

open class CalendarInfo2 {
    open val date: Calendar? = null
    open val isHoliday: Boolean = false
    open val isSpecialDay: Boolean = false

    open fun getDate(): Int?  {
        return date?.get(Calendar.DAY_OF_MONTH)
    }

    open fun itemSize(): Int {
        return 0
    }
}

data class ScheduleCalendarInfo(
    override val date: Calendar?= null,
    override val isHoliday: Boolean = false,
    override val isSpecialDay: Boolean = false,
    val dateInfo: String = "",
    val detailDate: String = "",
    val itemList: List<CalendarItem> = listOf(),
): CalendarInfo2() {
    override fun itemSize(): Int {
        return itemList.size
    }

    fun getDayInfo(): String {
        val sdfInput = SimpleDateFormat("MM월 dd일", Locale.getDefault())
        return date?.let { "${sdfInput.format(it.time)} ${getDayOfWeek()}" } ?: ""
    }

    fun getDayOfWeek(): String {
        return when (date?.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "일요일"
            Calendar.MONDAY -> "월요일"
            Calendar.TUESDAY -> "화요일"
            Calendar.WEDNESDAY -> "수요일"
            Calendar.THURSDAY -> "목요일"
            Calendar.FRIDAY -> "금요일"
            Calendar.SATURDAY -> "토요일"
            else -> ""
        }
    }
}

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

fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
    return cal1 != null && cal2 != null && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}