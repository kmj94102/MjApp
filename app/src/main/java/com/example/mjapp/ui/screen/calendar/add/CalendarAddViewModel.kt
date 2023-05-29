package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.network.model.PlanTasks
import com.example.network.model.ScheduleModifier
import com.example.network.model.TaskItem
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CalendarRepository
) : ViewModel() {

    private val _isSchedule = mutableStateOf(true)
    val isSchedule: State<Boolean> = _isSchedule

    private val _taskList = mutableStateListOf("")
    val taskList: List<String> = _taskList

    private val _scheduleModifier = mutableStateOf(ScheduleModifier())
    val scheduleModifier: State<ScheduleModifier> = _scheduleModifier

    private val _status = MutableStateFlow<Status>(Status.Init)
    val status: StateFlow<Status> = _status

    init {
        savedStateHandle.get<String>(NavScreen.CalendarAdd.Date)?.let {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                date = it,
                recurrenceEndDate = it
            )
        }
    }

    fun updateIsSchedule(value: Boolean) {
        _isSchedule.value = value
    }

    fun updateDate(value: String, isDate: Boolean) {
        if (isDate) {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                date = value
            )
        } else {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                recurrenceEndDate = value
            )
        }
    }

    fun updateTime(value: String, isStart: Boolean) {
        if (isStart) {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                startTime = value
            )
        } else {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                endTime = value
            )
        }
    }

    fun updateTitle(value: String) {
        _scheduleModifier.value = _scheduleModifier.value.copy(
            scheduleTitle = value
        )
    }

    fun updateContent(value: String) {
        _scheduleModifier.value = _scheduleModifier.value.copy(
            scheduleContent = value
        )
    }

    fun updateRecurrenceType(type: String) {
        _scheduleModifier.value = _scheduleModifier.value.copy(
            recurrenceType = type
        )
    }

    fun addPlanItem() {
        _taskList.add("")
    }

    fun removePlanItem(index: Int) {
        try {
            _taskList.removeAt(index)
            if (_taskList.size == 0) {
                _taskList.add("")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updatePlanContents(index: Int, value: String) {
        try {
            _taskList[index] = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertSchedule() = viewModelScope.launch {
        val validityResult = _scheduleModifier.value.checkValidity()
        if (validityResult.isNotEmpty()) {
            _status.value = Status.Failure(validityResult)
            return@launch
        }

        repository.insertSchedule(
            item = _scheduleModifier.value,
            onSuccess = {
                _status.value = Status.Success(it)
            },
            onFailure = {
                _status.value = Status.Failure(it)
            }
        )
    }

    fun insertPlan() = viewModelScope.launch {
        val planTasks = PlanTasks(
            title = _scheduleModifier.value.scheduleTitle,
            planDate = "${_scheduleModifier.value.date.replace(".", "-")}T00:00:00.000Z",
            taskList = _taskList.filter { it.isNotEmpty() }.map { TaskItem(contents = it) }
        )

        repository.insertPlan(
            item = planTasks,
            onSuccess = {
                _status.value = Status.Success(it)
            },
            onFailure = {
                _status.value = Status.Failure(it)
            }
        )
    }

    fun updateStatusInit() {
        _status.value = Status.Init
    }

    sealed class Status {
        object Init : Status()

        data class Success(
            val msg: String
        ) : Status()

        data class Failure(
            val msg: String
        ) : Status()
    }
}

