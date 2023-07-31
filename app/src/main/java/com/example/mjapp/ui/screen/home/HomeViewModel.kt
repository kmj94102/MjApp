package com.example.mjapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.custom.getLastDayOfWeek
import com.example.mjapp.ui.custom.getStartDayOfWeek
import com.example.mjapp.ui.custom.getWeeklyDateRange
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getToday
import com.example.mjapp.util.toStringFormat
import com.example.network.model.*
import com.example.network.repository.CalendarRepository
import com.example.network.repository.ElswordRepository
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val pokemonRepository: PokemonRepository,
    private val elswordRepository: ElswordRepository
) : BaseViewModel() {

    val today = getToday()
    private val _list = mutableStateListOf<MyCalendar>()
    val list: List<MyCalendar> = _list

    private val _pokemonList = mutableStateListOf<PokemonCounter>()
    val pokemonList: List<PokemonCounter> = _pokemonList

    private val _selectItem = mutableStateOf(MyCalendar())
    val selectItem: State<MyCalendar> = _selectItem

    private val _counterList = mutableStateListOf<ElswordCounter>()
    val counterList: List<ElswordCounter> = _counterList

    init {
        _list.addAll(getWeeklyDateRange(today))
        val index = _list.indexOfFirst { it.detailDate == today }
        _selectItem.value = _list.getOrElse(index) { MyCalendar() }
        fetchCalenderInfo()
        fetchPokemonCounter()
        fetchElswordCounter()
    }

    fun updateSelectDate(date: String) {
        val index = _list.indexOfFirst { it.detailDate == date }
        _selectItem.value = _list.getOrElse(index) { MyCalendar() }
    }

    private fun fetchCalenderInfo() {
        val start = getStartDayOfWeek(today)?.toStringFormat() ?: return
        val end = getLastDayOfWeek(today)?.toStringFormat() ?: return
        calendarRepository
            .fetchCalendarByWeek(
                start = start,
                end = end
            )
            .onEach {
                it.forEach { info ->
                    setCalendarItem(info)
                }
            }
            .launchIn(viewModelScope)
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
        calendarRepository.deleteSchedule(id)
    }

    fun deletePlanTasks(id: Int) = viewModelScope.launch {
        calendarRepository.deletePlanTasks(id)
    }

    private fun fetchPokemonCounter() {
        pokemonRepository
            .fetchPokemonCounter()
            .onEach {
                _pokemonList.clear()
                _pokemonList.addAll(it)
            }
            .catch {
                _pokemonList.clear()
            }
            .launchIn(viewModelScope)
    }

    fun updateCounter(
        count: Int,
        number: String
    ) = viewModelScope.launch {
        pokemonRepository.updateCounter(count, number)
    }

    fun deleteCounter(
        number: String
    ) = viewModelScope.launch {
        pokemonRepository.deletePokemonCounter(number)
    }

    fun updateCatch(
        number: String
    ) = viewModelScope.launch {
        pokemonRepository.updateCatch(number)
        pokemonRepository.updatePokemonCatch(
            UpdatePokemonCatch(
                number = number,
                isCatch = true
            )
        )
    }

    fun updateCustomIncrease(
        customIncrease: Int,
        number: String
    ) = viewModelScope.launch {
        pokemonRepository.updateCustomIncrease(customIncrease, number)
    }

    private fun fetchElswordCounter() {
        elswordRepository
            .fetchQuestCounter()
            .onEach {
                _counterList.clear()
                _counterList.addAll(it)
            }
            .launchIn(viewModelScope)
    }

    fun updateElswordCounter(
        index: Int
    ) = viewModelScope.launch {
        val selectItem = _counterList[index]
        val updateItem = ElswordCounterUpdateItem(
            id = selectItem.id,
            max = selectItem.max
        )

        val result = elswordRepository.updateQuestCounter(updateItem)
        if (result == 0) {
            _counterList.removeAt(index)
        }
        _counterList[index] = _counterList[index].copy(
            progress = result
        )
    }

}