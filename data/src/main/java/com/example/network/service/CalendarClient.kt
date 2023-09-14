package com.example.network.service

import com.example.network.model.HomeParam
import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleItem
import com.example.network.model.TaskUpdateItem
import javax.inject.Inject

class CalendarClient @Inject constructor(
    private val service: CalendarService
) {
    /** 홈 정보 조회 **/
    suspend fun fetchHomeInfo(item: HomeParam) = runCatching { service.fetchHomeInfo(item) }

    /** 월 정보 조회 **/
    suspend fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ) = runCatching { service.fetchCalendarByMonth(year, month) }

    /** 일정 등록 **/
    suspend fun insertSchedule(item: ScheduleItem) = runCatching { service.insertSchedule(item) }

    /** 계획 등록 **/
    suspend fun insertPlan(item: PlanTasks) = runCatching { service.insertPlan(item) }

    /** 일정 삭제 **/
    suspend fun deleteSchedule(id: Int) = runCatching { service.deleteSchedule(id) }

    /** 계획 삭제 **/
    suspend fun deletePlanTasks(id: Int) = runCatching { service.deletePlanTasks(id) }

    /** 계획 업데이트 **/
    suspend fun updateTaskItem(item: TaskUpdateItem) = runCatching { service.updateTask(item) }

}