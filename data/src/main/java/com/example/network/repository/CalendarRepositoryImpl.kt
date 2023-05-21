package com.example.network.repository

import com.example.network.model.ScheduleItem
import com.example.network.service.CalendarClient
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val client: CalendarClient
) : CalendarRepository {
    override suspend fun insertSchedule(
        item: ScheduleItem,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val calendarItem = item.toMyCalendarItem()

        try {
            client.insertSchedule(calendarItem)
            onSuccess("일정 등록 완료")
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure("일정 등록 실패")
        }
    }
}