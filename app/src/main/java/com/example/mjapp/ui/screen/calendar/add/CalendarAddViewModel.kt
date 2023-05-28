package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.network.model.ScheduleModifier
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

    private val _planList = mutableStateListOf("")
    val planList: List<String> = _planList

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
        _planList.add("")
    }

    fun removePlanItem(index: Int) {
        try {
            _planList.removeAt(index)
            if (_planList.size == 0) {
                _planList.add("")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updatePlanContents(index: Int, value: String) {
        try {
            _planList[index] = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertSchedule() = viewModelScope.launch {
        if (_scheduleModifier.value.checkValidity().not()) {
            _status.value = Status.Failure("데이터를 확인해주세요.")
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

