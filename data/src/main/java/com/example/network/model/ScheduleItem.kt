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

    fun checkValidity(): ScheduleModifier {
        return when {
            date.isEmpty() -> throw Exception("날짜를 선택해 주세요.")
            startTime.isEmpty() -> throw Exception("시작 시간을 선택해 주세요.")
            endTime.isEmpty() -> throw Exception("종료 시간을 선택해 주세요.")
            scheduleTitle.isEmpty() -> throw Exception("제목을 입력해 주세요.")
            scheduleContent.isEmpty() -> throw Exception("내용을 입력해 주세요.")
            recurrenceType != "none" && recurrenceEndDate.isEmpty() ->
                throw Exception("반복 설정 시 종료 시간을 설정해야 합니다.")

            recurrenceType != "none" && compareTime(date, recurrenceEndDate) >= 0 ->
                throw Exception("반복 설정 시 종료 시간은 일정 등록일 이후로 설정해야 합니다.")

            compareTime(startTime, endTime, "HH:mm") > 0 ->
                throw Exception("시작 시간 또는 종료 시간을 확인해 주세요.")

            else -> this
        }
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

    fun calculateEndTime(value: String): String {
        val result = compareTime(value, endTime.ifEmpty { "00:00" }, "HH:mm")
        return if (result > 0) value else endTime
    }
}