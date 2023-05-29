package com.example.network.model

import java.text.SimpleDateFormat
import java.util.*

data class ScheduleItem(
    val startTime: String,
    val endTime: String,
    val recurrenceType: String,
    val recurrenceEndDate: String?,
    val scheduleContent: String,
    val scheduleTitle: String,
)

data class ScheduleModifier(
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val recurrenceType: String = "none",
    val recurrenceEndDate: String = "",
    val scheduleContent: String = "",
    val scheduleTitle: String = "",
) {
    fun getRecurrenceInfo() = Recurrence.getRecurrenceKoreanName(recurrenceType)

    fun toMyCalendarItem() = ScheduleItem(
        startTime = toDateTimeFormat(date, startTime),
        endTime = toDateTimeFormat(date, endTime),
        recurrenceType = recurrenceType,
        recurrenceEndDate = getRecurrenceEndDate(recurrenceEndDate),
        scheduleContent = scheduleContent,
        scheduleTitle = scheduleTitle,
    )

    private fun toDateTimeFormat(date: String, time: String) =
        "${date.replace(".", "-")}T${time}:00.000Z"

    private fun getRecurrenceEndDate(recurrenceEndDate: String) =
        if (recurrenceType == "none" || recurrenceEndDate.isEmpty()) null
        else "${recurrenceEndDate.replace(".", "-")}T23:59:59.000Z"

    fun checkValidity(): String {
        if (recurrenceType != "none" && compareTime(date, recurrenceEndDate) >= 0) {
            return "반복 설정 시 종료 시간은 일정 등록일 이후로 설정해야 합니다."
        }
        if (compareTime(startTime, endTime, "HH:mm") > 0) {
            return "시작 시간 또는 종료 시간을 확인해주세요."
        }
        if (scheduleContent.isEmpty() || scheduleTitle.isEmpty()) {
            return "일정 제목 도는 내용을 추가해주세요."
        }
        return ""
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