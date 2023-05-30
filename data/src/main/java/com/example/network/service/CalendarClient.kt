package com.example.network.service

import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleItem
import javax.inject.Inject

class CalendarClient @Inject constructor(
    private val service: CalendarService
) {

    suspend fun insertSchedule(
        item: ScheduleItem
    ) = service.insertSchedule(item)

    suspend fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ) = service.fetchCalendarByMonth(year, month)

    suspend fun fetchCalendarByDate(
        year: Int,
        month: Int,
        date: Int
    ) = service.fetchCalendarByDate(year, month, date)

    suspend fun insertPlan(
        item: PlanTasks
    ) = service.insertPlan(item)

    suspend fun deleteSchedule(
        id: Int
    ) = service.deleteSchedule(id)

    suspend fun deletePlanTasks(
        id: Int
    ) = service.deletePlanTasks(id)

}