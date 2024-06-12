package com.example.mjapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.custom.getLastDayOfWeek
import com.example.mjapp.ui.custom.getStartDayOfWeek
import com.example.mjapp.ui.custom.getWeeklyDateRange
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getToday
import com.example.mjapp.util.toStringFormat
import com.example.network.model.ElswordCounterUpdateItem
import com.example.network.model.HomeParam
import com.example.network.model.MyCalendarInfo
import com.example.network.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    val today = getToday()
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        val list = getWeeklyDateRange(today)
        _state.value = _state.value.copy(
            list = list,
            index = list.indexOfFirst { it.detailDate == today }
        )
        fetchHomeInfo()
    }

    private fun fetchHomeInfo() {
        val start = getStartDayOfWeek(today)?.toStringFormat() ?: return
        val end = getLastDayOfWeek(today)?.toStringFormat() ?: return

        repository
            .fetchHomeInfo(HomeParam(startDate = start, endDate = end))
            .onStart { startLoading() }
            .onEach {
                _state.value = _state.value.copy(homeInfo = it)
                it.calendarInfo.forEach { info ->
                    setCalendarItem(info.toMyCalendarInfo())
                }
                endLoading()
            }
            .catch { it.printStackTrace() }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateSelectDate(date: String) {
        _state.value = _state.value.copy(
            index = _state.value.list.indexOfFirst { it.detailDate == date }
        )
    }

    private fun setCalendarItem(item: MyCalendarInfo) {
        val newList = _state.value.list.map {
            if (it.detailDate == item.date) {
                it.copy(
                    isHoliday = item.isHoliday,
                    isSpecialDay = item.isSpecialDay,
                    dateInfo = item.info,
                    itemList = item.list.toMutableList()
                )
            } else {
                it
            }
        }

        _state.value = _state.value.copy(
            list = newList
        )
    }

    fun deleteSchedule(id: Int) = viewModelScope.launch {
        repository.deleteSchedule(id)
    }

    fun deletePlanTasks(id: Int) = viewModelScope.launch {
        repository.deletePlanTasks(id)
    }

    fun updatePokemonSelectIndex(value: Int) {
        val newValue = _state.value.pokemonSelectIndex + value
        val listSize = _state.value.getPokemonCounterListSize()

        val newIndex = if (newValue < 0) {
            listSize - 1
        } else if (newValue >= listSize) {
            0
        } else {
            newValue
        }

        _state.value = _state.value.copy(
            pokemonSelectIndex = newIndex
        )
    }

    fun updateElswordCounter(index: Int) = viewModelScope.launch {
        val tempList = _state.value.getElswordQuestList().toMutableList()
        val updateItem = ElswordCounterUpdateItem(
            id = tempList[index].id,
            max = tempList[index].max
        )

        val result = repository.updateQuestCounter(updateItem)
        if (result == 0) {
            tempList.removeAt(index)
        } else {
            tempList[index] = tempList[index].copy(progress = result)
        }

        _state.value = _state.value.copy(
            homeInfo = _state.value.homeInfo.copy(questInfo = tempList)
        )
    }

}