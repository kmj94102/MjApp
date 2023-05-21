package com.example.network.repository

import com.example.network.model.ScheduleItem

interface CalendarRepository {

    suspend fun insertSchedule(
        item: ScheduleItem,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

}