package com.example.network.service

import com.example.network.model.MyCalendarItem
import retrofit2.http.Body
import retrofit2.http.POST

interface CalendarService {
    @POST("/insert/schedule")
    suspend fun insertSchedule(@Body item: MyCalendarItem)
}