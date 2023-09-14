package com.example.network.model

data class PlanTasks(
    val title: String,
    val planDate: String,
    val taskList: List<TaskItem>
)

data class TaskItem(
    val planId: Int = 0,
    val contents: String,
    val isCompleted: Boolean = false
)

data class PlanTasksModify(
    val title: String = "",
    val planDate: String = "",
    val taskList: List<TaskItem> = mutableListOf(TaskItem(contents = ""))
) {
    fun checkValidity() =
        when {
            title.isEmpty() -> throw Exception("제목을 입력해 주세요.")
            planDate.isEmpty() -> throw Exception("계획 날짜를 지정해 주세요.")
            taskList.none { it.contents.trim().isNotEmpty() } -> throw Exception("계획을 한 개 이상 등록해 주세요")
            else -> this
        }

    fun toPlanTasks() = PlanTasks(
        title = title,
        planDate = "${planDate.replace(".", "-")}T00:00:00.000Z",
        taskList = taskList.filter { it.contents.trim().isNotEmpty() }
    )
}

data class TaskUpdateItem(
    val id: Int,
    val isCompleted: Boolean
)