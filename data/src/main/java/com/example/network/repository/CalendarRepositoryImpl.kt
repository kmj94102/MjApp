package com.example.network.repository

import com.example.network.model.MyCalendarInfo
import com.example.network.model.ScheduleModifier
import com.example.network.service.CalendarClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val client: CalendarClient
) : CalendarRepository {
    override suspend fun insertSchedule(
        item: ScheduleModifier,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val calendarItem = item.toMyCalendarItem()

        try {
            client.insertSchedule(calendarItem)
            onSuccess("일정 등록 완료")
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure("일정 등록 실패")
        }
    }

    override fun fetchCalendar(
        year: Int,
        month: Int
    ): Flow<List<MyCalendarInfo>> = flow {

        try {
            emit(
                client.fetchCalendar(year, month).map { it.toMyCalendarInfo() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }

    }
}