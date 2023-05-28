package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.ScheduleModifier
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    suspend fun insertSchedule(
        item: ScheduleModifier,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun fetchCalendar(
        year: Int,
        month: Int
    ): Flow<List<MyCalendarInfo>>
}