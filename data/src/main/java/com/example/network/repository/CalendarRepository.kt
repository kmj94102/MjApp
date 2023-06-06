package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleModifier
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    suspend fun insertSchedule(
        item: ScheduleModifier,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ): Flow<List<MyCalendarInfo>>

    fun fetchCalendarByWeek(
        start: String,
        end: String
    ): Flow<List<MyCalendarInfo>>

    suspend fun fetchCalendarByDate(
        currentDate: String
    ): MyCalendarInfo?

    suspend fun insertPlan(
        item: PlanTasks,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun deleteSchedule(id: Int)

    suspend fun deletePlanTasks(id: Int)

}