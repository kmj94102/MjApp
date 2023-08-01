package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.getToday
import com.example.network.model.ScheduleModifier
import com.example.network.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CalendarRepository
) : BaseViewModel() {

    val initDate = savedStateHandle.get<String>(Constants.Date) ?: "2023.01.01"
    private var selectItem = ScheduleDate

    private val _scheduleModifier = mutableStateOf(ScheduleModifier())
    val scheduleModifier: State<ScheduleModifier> = _scheduleModifier

    private val _selectDate = mutableStateOf("")
    val selectDate: State<String> = _selectDate

    private val _selectTime = mutableStateOf("")
    val selectTime: State<String> = _selectTime

    init {
        updateDate(initDate)
    }

    fun updateSelectItem(type: String) {
        when (type) {
            ScheduleDate -> {
                selectItem = type
                _selectDate.value = _scheduleModifier.value.date.ifEmpty { getToday() }
            }

            RecurrenceEndDate -> {
                selectItem = type
                _selectDate.value = _scheduleModifier.value.recurrenceEndDate.ifEmpty { getToday() }
            }

            StartTime -> {
                selectItem = type
                _selectTime.value = _scheduleModifier.value.startTime.ifEmpty { "00:00" }
            }

            EndTime -> {
                selectItem = type
                _selectTime.value = _scheduleModifier.value.endTime.ifEmpty { "00:00" }
            }
        }
    }

    fun updateTitle(value: String) {
        _scheduleModifier.value = _scheduleModifier.value.copy(scheduleTitle = value)
    }

    fun updateContent(value: String) {
        _scheduleModifier.value = _scheduleModifier.value.copy(scheduleContent = value)
    }

    fun updateRecurrence(value: String) {
        _scheduleModifier.value = _scheduleModifier.value.copy(recurrenceType = value)
    }

    fun updateDate(value: String) {
        if (selectItem == ScheduleDate) {
            _scheduleModifier.value = _scheduleModifier.value.copy(date = value)
        } else if (selectItem == RecurrenceEndDate) {
            _scheduleModifier.value = _scheduleModifier.value.copy(recurrenceEndDate = value)
        }
    }

    fun updateTime(value: String) {
        if (selectItem == StartTime) {
            _scheduleModifier.value = _scheduleModifier.value.copy(
                startTime = value,
                endTime = _scheduleModifier.value.calculateEndTime(value)
            )
        } else if (selectItem == EndTime) {
            _scheduleModifier.value = _scheduleModifier.value.copy(endTime = value)
        }
    }

    fun insertSchedule() = viewModelScope.launch {
        repository
            .insertSchedule(item = _scheduleModifier.value)
            .onSuccess {
                updateMessage(it)
                updateFinish()
            }
            .onFailure { e ->
                e.message?.let { updateMessage(it) } ?: updateMessage("등록 중 오류가 발생하였습니다.")
            }
    }

    companion object {
        const val ScheduleDate = "scheduleDate"
        const val RecurrenceEndDate = "recurrenceEndDate"
        const val StartTime = "startTime"
        const val EndTime = "endTime"
    }

}