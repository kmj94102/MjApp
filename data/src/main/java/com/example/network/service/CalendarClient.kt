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

    suspend fun fetchCalendar(
        year: Int,
        month: Int
    ) = service.fetchCalendar(year, month)

    suspend fun insertPlan(
        item: PlanTasks
    ) = service.insertPlan(item)

}