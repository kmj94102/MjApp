package com.example.network.service

import com.example.network.model.CalendarResult
import com.example.network.model.HomeInfoResult
import com.example.network.model.HomeParam
import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleItem
import com.example.network.model.TaskUpdateItem
import retrofit2.http.*

interface CalendarService {
    /** 홈 정보 조회 **/
    @POST("/homeInfo/select")
    suspend fun fetchHomeInfo(@Body item: HomeParam): HomeInfoResult

    /** 달력 정보 조회 **/
    @GET("/calendar/select/month")
    suspend fun fetchCalendarByMonth(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<CalendarResult>

    /** 일정 등록 **/
    @POST("/calendar/insert/schedule")
    suspend fun insertSchedule(@Body item: ScheduleItem): String

    /** 계획 등록 **/
    @POST("/calendar/insert/planTasks")
    suspend fun insertPlan(@Body item: PlanTasks): String

    /** 일정 삭제 **/
    @DELETE("/calendar/delete/schedule")
    suspend fun deleteSchedule(@Query("id") id: Int)

    /** 계획 삭제 **/
    @DELETE("/calendar/delete/planTasks")
    suspend fun deletePlanTasks(@Query("id") id: Int)

    /** 계획 업데이트 **/
    @POST("/calendar/update/task")
    suspend fun updateTask(@Body item: TaskUpdateItem): CalendarResult

}