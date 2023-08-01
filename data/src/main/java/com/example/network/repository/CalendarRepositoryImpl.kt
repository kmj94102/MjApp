package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleModifier
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

        try {
            emit(
                client.fetchCalendarByMonth(year, month).map { it.toMyCalendarInfo() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }

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

    override suspend fun fetchCalendarByDate(currentDate: String): MyCalendarInfo? {
        return try {
            val year = currentDate.substring(0, 4).toInt()
            val month = currentDate.substring(5, 7).toInt()
            val date = currentDate.substring(8, 10).toInt()

            client.fetchCalendarByDate(year, month, date)?.toMyCalendarInfo()
        } catch (e: Exception) {
            e.printStackTrace()
            null
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