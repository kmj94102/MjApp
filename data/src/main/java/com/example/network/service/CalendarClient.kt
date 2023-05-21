package com.example.network.service

import com.example.network.model.MyCalendarItem
import javax.inject.Inject

class CalendarClient @Inject constructor(
    private val service: CalendarService
) {

    suspend fun insertSchedule(
        item: MyCalendarItem
    ) = service.insertSchedule(item)

}