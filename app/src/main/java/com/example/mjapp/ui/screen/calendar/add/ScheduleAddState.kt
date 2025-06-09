package com.example.mjapp.ui.screen.calendar.add

import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleModifier

data class ScheduleAddState(
    val scheduleInfo: ScheduleModifier = ScheduleModifier(),
    val planInfo: PlanTasksModify = PlanTasksModify(),
    val isSchedule: Boolean = true,
)