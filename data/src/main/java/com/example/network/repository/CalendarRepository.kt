package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleModifier
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    suspend fun insertSchedule(item: ScheduleModifier): Result<String>

    fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ): Flow<List<MyCalendarInfo>>

    fun fetchCalendarByWeek(
        start: String,
        end: String
    ): Flow<List<MyCalendarInfo>>

    suspend fun insertPlan(item: PlanTasksModify): Result<String>

    suspend fun deleteSchedule(id: Int)

    suspend fun deletePlanTasks(id: Int)

}