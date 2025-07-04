package com.example.network.repository

import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleCalendarInfo
import com.example.network.model.ScheduleModifier
import com.example.network.model.TaskUpdateItem
import com.example.network.model.getFailureThrow
import com.example.network.model.getMonthList
import com.example.network.service.CalendarClient
import kotlinx.coroutines.flow.flow
import java.util.Calendar
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
            .onSuccess { list ->
                val calendarList = mapCalendarInfo(year, month)
                val dataList = list.mapNotNull { it.toScheduleCalendarInfo() }

                val resultList = (calendarList + dataList)
                    .associateBy { it.date?.get(Calendar.DAY_OF_MONTH) }
                    .values
                    .toList()
                emit(resultList)
            }
            .getFailureThrow()
    }

    fun mapCalendarInfo(
        year: Int,
        month: Int,
    ): List<ScheduleCalendarInfo> {
        return getMonthList(year, month).map { ScheduleCalendarInfo(date = it) }
    }

    /** 일정 등록 **/
    override fun insertSchedule(item: ScheduleModifier) = flow {
        client.insertSchedule(item.checkValidity().toMyCalendarItem()).getFailureThrow()
        emit("일정 등록 완료")
    }

    /** 계획 등록 **/
    override fun insertPlan(item: PlanTasksModify) = flow {
        client.insertPlan(item.checkValidity().toPlanTasks()).getFailureThrow()
        emit("계획 등록 완료")
    }

    /** 일정 삭제 **/
    override suspend fun deleteSchedule(id: Int) {
        client.deleteSchedule(id).getFailureThrow()
    }

    /** 계획 삭제 **/
    override suspend fun deletePlanTasks(id: Int) {
        client.deletePlanTasks(id).getFailureThrow()
    }

    /** 계획 업데이트 **/
    override fun updateTask(id: Int, isCompleted: Boolean, date: String) = flow {
        client
            .updateTaskItem(TaskUpdateItem(id, isCompleted, date))
            .onSuccess { emit(it.toMyCalendarInfo()) }
            .getFailureThrow()
    }

}