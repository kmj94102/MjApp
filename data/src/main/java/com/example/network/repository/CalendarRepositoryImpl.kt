package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleModifier
import com.example.network.model.getFailureThrow
import com.example.network.service.CalendarClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val client: CalendarClient
) : CalendarRepository {
    override suspend fun insertSchedule(item: ScheduleModifier) = runCatching {
        client.insertSchedule(item.checkValidity().toMyCalendarItem())
        "일정 등록 완료"
    }

    override fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ): Flow<List<MyCalendarInfo>> = flow {
        client
            .fetchCalendarByMonth(year, month)
            .onSuccess { list ->
                emit(list.map { it.toMyCalendarInfo() })
            }
            .getFailureThrow()
    }

    override fun fetchCalendarByWeek(start: String, end: String) = flow {
        try {
            emit(
                client
                    .fetchCalendarByWeek(start, end)
                    .map { it.toMyCalendarInfo() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override suspend fun insertPlan(item: PlanTasksModify) = runCatching {
        client.insertPlan(item.checkValidity().toPlanTasks())
        "계획 등록 완료"
    }

    override suspend fun deleteSchedule(id: Int) {
        try {
            client.deleteSchedule(id)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun deletePlanTasks(id: Int) {
        try {
            client.deletePlanTasks(id)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}