package com.example.network.service

import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleItem
import javax.inject.Inject

class CalendarClient @Inject constructor(
    private val service: CalendarService
) {

    suspend fun insertSchedule(
        item: ScheduleItem
    ) = runCatching { service.insertSchedule(item) }

    suspend fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ) = runCatching { service.fetchCalendarByMonth(year, month) }

    suspend fun fetchCalendarByWeek(
        start: String,
        end: String
    ) = service.fetchCalendarByWeek(start, end)

    suspend fun insertPlan(
        item: PlanTasks
    ) = runCatching { service.insertPlan(item) }

    suspend fun deleteSchedule(
        id: Int
    ) = service.deleteSchedule(id)

    suspend fun deletePlanTasks(
        id: Int
    ) = service.deletePlanTasks(id)

}