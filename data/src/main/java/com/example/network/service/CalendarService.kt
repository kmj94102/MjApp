package com.example.network.service

import com.example.network.model.CalendarResult
import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleItem
import retrofit2.http.*

interface CalendarService {
    @POST("/insert/schedule")
    suspend fun insertSchedule(@Body item: ScheduleItem)

    @GET("/select/calendar/month")
    suspend fun fetchCalendarByMonth(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<CalendarResult>

    @GET("/select/calendar/date")
    suspend fun fetchCalendarByDate(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("date") date: Int
    ): CalendarResult?

    @POST("/insert/plan-tasks")
    suspend fun insertPlan(@Body item: PlanTasks)

    @DELETE("/delete/schedule")
    suspend fun deleteSchedule(@Query("id") id: Int)

    @DELETE("/delete/plan-tasks")
    suspend fun deletePlanTasks(@Query("id") id: Int)

}