package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.calendar.PlanTasksModify
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.util.getToday
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

    private val _scheduleModifier = mutableStateOf(ScheduleModifier())
    val scheduleModifier: State<ScheduleModifier> = _scheduleModifier

    private val _planTasks = mutableStateOf(PlanTasksModify())
    val planTasks: State<PlanTasksModify> = _planTasks

    private val _selectDate = mutableStateOf("2020.01.01")
    val selectDate: State<String> = _selectDate

    private val _selectTime = mutableStateOf("00:00")
    val selectTime: State<String> = _selectTime

    private val selectItem = mutableStateOf(ScheduleDate)

    private val _status = MutableStateFlow<Status>(Status.Init)
    val status: StateFlow<Status> = _status

    init {
        savedStateHandle.get<String>(NavScreen.CalendarAdd.Date)?.let {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                date = it
            )
            _planTasks.value = _planTasks.value.copy(
                planDate = it
            )
        }
    }

    fun updateIsSchedule(value: Boolean) {
        _isSchedule.value = value
    }

    fun updateItem(
        value: String = "",
        type: String
    ) {
        selectItem.value = type
        when (type) {
            ScheduleDate -> {
                _selectDate.value = _scheduleModifier.value.date.ifEmpty { getToday() }
            }
            StartTime -> {
                _selectTime.value = _scheduleModifier.value.startTime.ifEmpty { "00:00" }
            }
            EndTime -> {
                _selectTime.value = _scheduleModifier.value.endTime
                    .ifEmpty { _scheduleModifier.value.startTime.ifEmpty { "00:00" } }
            }
            RecurrenceType -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    recurrenceType = value
                )
            }
            RecurrenceEndDate -> {
                _selectDate.value = _scheduleModifier.value.recurrenceEndDate
                    .ifEmpty { _scheduleModifier.value.date.ifEmpty { getToday() } }
            }
            ScheduleContent -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    scheduleContent = value
                )
            }
            ScheduleTitle -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    scheduleTitle = value
                )
            }
            PlanDate -> {
                _planTasks.value = _planTasks.value.copy(
                    planDate = value
                )
            }
            PlanTitle -> {
                _planTasks.value = _planTasks.value.copy(
                    title = value
                )
            }
        }
    }

    fun updateDate(value: String) {
        when (selectItem.value) {
            ScheduleDate -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    date = value
                )
            }
            RecurrenceEndDate -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    recurrenceEndDate = value
                )
            }
            PlanDate -> {
                _planTasks.value = _planTasks.value.copy(
                    planDate = value
                )
            }
        }
    }

    fun updateTime(value: String) {
        when (selectItem.value) {
            StartTime -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    startTime = value
                )
            }
            EndTime -> {
                _scheduleModifier.value = _scheduleModifier.value.copy(
                    endTime = value
                )
            }
        }
    }

    fun addPlanItem() {
        _planTasks.value.taskList.add(TaskItem(contents = ""))
    }

    fun removePlanItem(index: Int) {
        try {
            _planTasks.value.taskList.removeAt(index)
            if ( _planTasks.value.taskList.size == 0) {
                _planTasks.value.taskList.add(TaskItem(contents = ""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updatePlanContents(index: Int, value: String) {
        try {
            _planTasks.value.taskList[index] = TaskItem(contents = value)
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
        val validityResult = _planTasks.value.checkValidity()

        if (validityResult.isNotEmpty()) {
            _status.value = Status.Failure(validityResult)
            return@launch
        }

        repository.insertPlan(
            item = _planTasks.value.toPlanTasks(),
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

    companion object {
        const val ScheduleDate = "scheduleDate"
        const val StartTime = "startTime"
        const val EndTime = "endTime"
        const val RecurrenceType = "recurrenceType"
        const val RecurrenceEndDate = "recurrenceEndDate"
        const val ScheduleContent = "scheduleContent"
        const val ScheduleTitle = "scheduleTitle"

        const val PlanDate = "planDate"
        const val PlanTitle = "planTitle"
    }
}

