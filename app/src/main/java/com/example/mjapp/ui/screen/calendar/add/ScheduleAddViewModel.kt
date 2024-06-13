package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.getToday
import com.example.mjapp.util.update
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
    private var selectItem = SCHEDULE_DATE

    private val _scheduleModifier = mutableStateOf(ScheduleModifier())
    val scheduleModifier: State<ScheduleModifier> = _scheduleModifier

    init {
        updateDate(initDate)
    }

    fun updateSelectItem(type: String) {
        when (type) {
            SCHEDULE_DATE -> {
                selectItem = type
                _scheduleModifier.update {
                    it.copy(selectDate = it.date.ifEmpty { getToday() })
                }
            }

            RECURRENCE_END_DATE -> {
                selectItem = type
                _scheduleModifier.update {
                    it.copy(selectDate = it.recurrenceEndDate.ifEmpty { getToday() })
                }
            }

            START_TIME -> {
                selectItem = type
                _scheduleModifier.update {
                    it.copy(selectTime = it.startTime.ifEmpty { "00:00" })
                }
            }

            END_TIME -> {
                selectItem = type
                _scheduleModifier.update {
                    it.copy(selectTime = it.endTime.ifEmpty { "00:00" })
                }
            }
        }
    }

    fun updateTitle(value: String) {
        _scheduleModifier.update { it.copy(scheduleTitle = value) }
    }

    fun updateContent(value: String) {
        _scheduleModifier.update { it.copy(scheduleContent = value) }
    }

    fun updateRecurrence(value: String) {
        _scheduleModifier.update { it.copy(recurrenceType = value) }
    }

    fun updateDate(value: String) {
        if (selectItem == SCHEDULE_DATE) {
            _scheduleModifier.update { it.copy(date = value) }
        } else if (selectItem == RECURRENCE_END_DATE) {
            _scheduleModifier.update { it.copy(recurrenceEndDate = value) }
        }
    }

    fun updateTime(value: String) {
        if (selectItem == START_TIME) {
            _scheduleModifier.update {
                it.copy(
                    startTime = value,
                    endTime = _scheduleModifier.value.calculateEndTime(value)
                )
            }
        } else if (selectItem == END_TIME) {
            _scheduleModifier.update { it.copy(endTime = value) }
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
        const val SCHEDULE_DATE = "scheduleDate"
        const val RECURRENCE_END_DATE = "recurrenceEndDate"
        const val START_TIME = "startTime"
        const val END_TIME = "endTime"
    }

}