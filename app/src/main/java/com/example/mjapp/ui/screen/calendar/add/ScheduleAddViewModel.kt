package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.PlanTasksModify
import com.example.network.model.ScheduleModifier
import com.example.network.model.TaskItem
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScheduleAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CalendarRepository
) : BaseViewModel() {
    private val _state = mutableStateOf(ScheduleAddState())
    val state: State<ScheduleAddState> = _state

    init {
        val initDate = savedStateHandle.toRoute<NavScreen2.ScheduleAdd>().date
        _state.update {
            it.copy(
                scheduleInfo = ScheduleModifier(date = initDate),
                planInfo = PlanTasksModify(planDate = initDate)
            )
        }
    }

    fun updateIsSchedule(value: Boolean) {
        _state.update { it.copy(isSchedule = value) }
    }

    fun updateSchedule(value: ScheduleModifier) {
        _state.update { it.copy(scheduleInfo = value) }
    }

    fun updatePlan(value: PlanTasksModify) {
        _state.update { it.copy(planInfo = value) }
    }

    fun updateAddTask() {
        val newList = state.value.planInfo.taskList.toMutableList()
        newList.add(TaskItem(contents = ""))

        _state.update { it.copy(planInfo = it.planInfo.copy(taskList = newList)) }
    }

    fun updateDate(value: String) {
        _state.update {
            it.copy(
                scheduleInfo = it.scheduleInfo.copy(date = value),
                planInfo = it.planInfo.copy(planDate = value)
            )
        }
    }

    fun updateStartTime(value: String) {
        _state.update {
            it.copy(
                scheduleInfo = it.scheduleInfo.copy(startTime = value),
            )
        }
    }

    fun updateEndTime(value: String) {
        _state.update {
            it.copy(
                scheduleInfo = it.scheduleInfo.copy(endTime = value),
            )
        }
    }

    fun updateRecurrenceType(value: String) {
        _state.update {
            it.copy(
                scheduleInfo = it.scheduleInfo.copy(recurrenceType = value),
            )
        }
    }

    fun submit() {
        if(_state.value.isSchedule) {
            insertSchedule()
        } else {
            insertPlan()
        }
    }

    private fun insertSchedule()  {
        repository
            .insertSchedule(_state.value.scheduleInfo)
            .setLoadingState()
            .onEach {
                updateMessage("일정 등록 완료")
                updateFinish()
            }
            .catch {
                it.message?.let { message -> updateMessage(message) } ?: updateMessage("일정 등록 실패")
            }
            .launchIn(viewModelScope)
    }

    private fun insertPlan()  {
        repository
            .insertPlan(_state.value.planInfo)
            .setLoadingState()
            .onEach {
                updateMessage("계획 등록 완료")
                updateFinish()
            }
            .catch {
                it.message?.let { message -> updateMessage(message) } ?: updateMessage("계획 등록 실패")
            }
            .launchIn(viewModelScope)
    }

}