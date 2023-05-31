package com.example.mjapp.ui.screen.calendar

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.network.model.PlanTasks
import com.example.network.model.TaskItem

data class PlanTasksModify(
    val title: String = "",
    val planDate: String = "",
    val taskList: SnapshotStateList<TaskItem> = mutableStateListOf(TaskItem(contents = ""))
) {
    fun checkValidity(): String {
        if (title.isEmpty()) return "제목을 입력해 주세요."
        if (planDate.isEmpty()) return "계획 날짜를 지정해 주세요."
        if (taskList.isEmpty()) return "계획을 한 개 이상 등록해 주세요"
        return ""
    }

    fun toPlanTasks() = PlanTasks(
        title = title,
        planDate = "${planDate.replace(".", "-")}T00:00:00.000Z",
        taskList = taskList
    )
}