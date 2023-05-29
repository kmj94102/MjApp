package com.example.network.service

import com.example.network.model.CalendarResult
import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CalendarService {
    @POST("/insert/schedule")
    suspend fun insertSchedule(@Body item: ScheduleItem)

    @GET("/select/calendar/month")
    suspend fun fetchCalendar(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<CalendarResult>

    @POST("/insert/plan-tasks")
    suspend fun insertPlan(@Body item: PlanTasks)

}