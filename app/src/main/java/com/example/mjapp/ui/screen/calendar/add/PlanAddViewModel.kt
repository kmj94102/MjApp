package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.network.model.PlanTasksModify
import com.example.network.model.TaskItem
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CalendarRepository
) : BaseViewModel() {

    val initDate = savedStateHandle.get<String>(Constants.Date) ?: "2023.01.01"

    private val _planTasks = mutableStateOf(PlanTasksModify())
    val planTasks: State<PlanTasksModify> = _planTasks

    init {
        updateDate(initDate)
    }

    fun addPlanItem() {
        _planTasks.value = _planTasks.value.copy(
            taskList = _planTasks.value.taskList.toMutableList().apply {
                add(TaskItem(contents = ""))
            }
        )
    }

    fun removePlanItem(index: Int) = runCatching {
        _planTasks.value = _planTasks.value.copy(
            taskList = _planTasks.value.taskList.toMutableList().apply {
                removeAt(index)
                if (isEmpty()) {
                    add(TaskItem(contents = ""))
                }
            }
        )
    }.onFailure { it.printStackTrace() }

    fun updatePlanContents(index: Int, value: String) = runCatching {
        _planTasks.value = _planTasks.value.copy(
            taskList = _planTasks.value.taskList.toMutableList().apply {
                set(index, TaskItem(contents = value))
            }
        )
    }.onFailure { it.printStackTrace() }

    fun updateTitle(value: String) {
        _planTasks.value = _planTasks.value.copy(title = value)
    }

    fun updateDate(value: String) {
        _planTasks.value = _planTasks.value.copy(planDate = value)
    }

    fun insertPlan() = viewModelScope.launch {
        repository
            .insertPlan(_planTasks.value)
            .onSuccess {
                updateMessage(it)
                updateFinish()
            }
            .onFailure { e ->
                e.message?.let { updateMessage(it) } ?: updateMessage("등록 중 오류가 발생하였습니다.")
            }
    }

}