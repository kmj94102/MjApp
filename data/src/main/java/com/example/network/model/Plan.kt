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
