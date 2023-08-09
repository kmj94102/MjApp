package com.example.network.repository

import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleModifier
import com.example.network.model.getFailureThrow
import com.example.network.service.CalendarClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val client: CalendarClient
) : CalendarRepository {
    /** 달력 정보 조회 **/
    override fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ) = flow {
        client
            .fetchCalendarByMonth(year, month)
            .onSuccess { list -> emit(list.map { it.toMyCalendarInfo() }) }
            .getFailureThrow()
    }

    /** 일정 등록 **/
    override suspend fun insertSchedule(item: ScheduleModifier) = runCatching {
        client.insertSchedule(item.checkValidity().toMyCalendarItem()).getFailureThrow()
        "일정 등록 완료"
    }

    /** 계획 등록 **/
    override suspend fun insertPlan(item: PlanTasksModify) = runCatching {
        client.insertPlan(item.checkValidity().toPlanTasks()).getFailureThrow()
        "계획 등록 완료"
    }

    /** 일정 삭제 **/
    override suspend fun deleteSchedule(id: Int) {
        client.deleteSchedule(id).getFailureThrow()
    }

    /** 계획 삭제 **/
    override suspend fun deletePlanTasks(id: Int) {
        client.deletePlanTasks(id).getFailureThrow()
    }
}