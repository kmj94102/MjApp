package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleCalendarInfo
import com.example.network.model.ScheduleModifier
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    /** 달력 정보 조회 **/
    fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ): Flow<List<ScheduleCalendarInfo>>

    /** 일정 등록 **/
    fun insertSchedule(item: ScheduleModifier): Flow<String>

    /** 계획 등록 **/
    fun insertPlan(item: PlanTasksModify): Flow<String>

    /** 일정 삭제 **/
    suspend fun deleteSchedule(id: Int)

    /** 계획 삭제 **/
    suspend fun deletePlanTasks(id: Int)

    /** 계획 업데이트 **/
    fun updateTask(id: Int, isCompleted: Boolean, date: String): Flow<MyCalendarInfo>

}