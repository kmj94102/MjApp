package com.example.network.model

import java.text.SimpleDateFormat
import java.util.*

data class MyCalendarItemResult(
    val startTime: String?,
    val endTime: String?,
    val recurrenceType: String?,
    val recurrenceEndDate: String?,
    val scheduleContent: String?,
    val scheduleTitle: String?,
    val scheduleType: String?
) {
    fun toMyCalendarItem(): MyCalendarItem? {
        return MyCalendarItem(
            startTime = startTime ?: return null,
            endTime = endTime ?: return null,
            recurrenceType = recurrenceType ?: return null,
            recurrenceEndDate = recurrenceEndDate,
            scheduleContent = scheduleContent ?: return null,
            scheduleTitle = scheduleTitle ?: return null,
            scheduleType = scheduleType ?: return null,
        )
    }
}

data class MyCalendarItem(
    val startTime: String,
    val endTime: String,
    val recurrenceType: String,
    val recurrenceEndDate: String?,
    val scheduleContent: String,
    val scheduleTitle: String,
    val scheduleType: String
) {
    companion object {
        const val Plan = "plan"
        const val Schedule = "schedule"
    }
}

data class ScheduleItem(
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val recurrenceType: String = "none",
    val recurrenceEndDate: String = "",
    val scheduleContent: String = "",
    val scheduleTitle: String = "",
    val scheduleType: String = "",
    val planList: MutableList<String> = mutableListOf("")
) {
    fun getRecurrenceInfo() = Recurrence.getRecurrenceKoreanName(recurrenceType)

    fun toMyCalendarItem() = MyCalendarItem(
        startTime = toDateTimeFormat(date, startTime),
        endTime = toDateTimeFormat(date, endTime),
        recurrenceType = recurrenceType,
        recurrenceEndDate = getRecurrenceEndDate(recurrenceEndDate),
        scheduleContent = scheduleContent,
        scheduleTitle = scheduleTitle,
        scheduleType = MyCalendarItem.Schedule
    )

    private fun toDateTimeFormat(date: String, time: String) =
        "${date.replace(".", "-")}T${time}:00.000Z"

    private fun getRecurrenceEndDate(recurrenceEndDate: String) =
        if (recurrenceType == "none" || recurrenceEndDate.isEmpty()) null
        else "${recurrenceEndDate.replace(".", "-")}T23:59:59.000Z"

    fun checkValidity(): Boolean {
        if (recurrenceType != "none" && compareTime(recurrenceEndDate, date) >= 0) return false
        if (compareTime(startTime, endTime, "HH:mm") > 0) return false
        if (scheduleContent.isEmpty() || scheduleTitle.isEmpty()) return false
        return true
    }

    private fun compareTime(
        time1: String,
        time2: String,
        pattern: String = "yyyy.MM.dd"
    ): Int {
        val format = SimpleDateFormat(pattern, Locale.KOREA)
        val date1: Date = format.parse(time1) ?: return 0
        val date2: Date = format.parse(time2) ?: return 0
        return date1.compareTo(date2)
    }
}