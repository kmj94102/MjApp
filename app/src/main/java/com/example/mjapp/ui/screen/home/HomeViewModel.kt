package com.example.mjapp.ui.screen.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.custom.getLastDayOfWeek
import com.example.mjapp.ui.custom.getStartDayOfWeek
import com.example.mjapp.ui.custom.getWeeklyDateRange
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getToday
import com.example.mjapp.util.toStringFormat
import com.example.network.model.ElswordCounter
import com.example.network.model.ElswordCounterUpdateItem
import com.example.network.model.HomeInfoResult
import com.example.network.model.HomeParam
import com.example.network.model.MyCalendar
import com.example.network.model.MyCalendarInfo
import com.example.network.model.PokemonCounter
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
    private val homeInfo = mutableStateOf(HomeInfoResult())

    private val _list = mutableStateListOf<MyCalendar>()
    val list: List<MyCalendar> = _list

    private val pokemonList: List<PokemonCounter> get() = homeInfo.value.pokemonInfo

    private val pokemonSelectIndex = mutableIntStateOf(0)
    val pokemonItem: PokemonCounter? get() = runCatching {
        pokemonList[pokemonSelectIndex.intValue]
    }.getOrNull()

    val itemSelectInfo: String get() =
        "${pokemonSelectIndex.intValue + 1}/${pokemonList.size}"

    val elswordQuestList: List<ElswordCounter> get() = homeInfo.value.questInfo

    private val index = mutableIntStateOf(0)
    val selectItem: MyCalendar get() = _list.getOrElse(index.intValue) { MyCalendar() }

    init {
        _list.addAll(getWeeklyDateRange(today))
        index.intValue = _list.indexOfFirst { it.detailDate == today }
        fetchHomeInfo()
    }

    private fun fetchHomeInfo() {
        val start = getStartDayOfWeek(today)?.toStringFormat() ?: return
        val end = getLastDayOfWeek(today)?.toStringFormat() ?: return

        repository
            .fetchHomeInfo(HomeParam(startDate = start, endDate = end))
            .onStart { startLoading() }
            .onEach {
                homeInfo.value = it
                homeInfo.value.calendarInfo.forEach { info ->
                    setCalendarItem(info.toMyCalendarInfo())
                }
                endLoading()
            }
            .catch { it.printStackTrace() }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateSelectDate(date: String) {
        index.intValue = _list.indexOfFirst { it.detailDate == date }
    }

    private fun setCalendarItem(item: MyCalendarInfo) {
        val index = _list.indexOfFirst { myCalendar ->
            myCalendar.detailDate == item.date
        }

        if (index != -1) {
            _list[index] = _list[index].copy(
                isHoliday = item.isHoliday,
                isSpecialDay = item.isSpecialDay,
                dateInfo = item.info,
                itemList = item.list.toMutableList()
            )
        }
    }

    fun deleteSchedule(id: Int) = viewModelScope.launch {
        repository.deleteSchedule(id)
    }

    fun deletePlanTasks(id: Int) = viewModelScope.launch {
        repository.deletePlanTasks(id)
    }

    fun updatePokemonSelectIndex(value: Int) {
        val newValue = pokemonSelectIndex.intValue + value
        pokemonSelectIndex.intValue = if (newValue < 0) {
            pokemonList.size - 1
        } else if (newValue >= pokemonList.size) {
            0
        } else {
            newValue
        }
    }

    fun updateElswordCounter(index: Int) = viewModelScope.launch {
        val tempList = elswordQuestList.toMutableList()
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
        homeInfo.value = homeInfo.value.copy(
            questInfo = tempList
        )
    }

}