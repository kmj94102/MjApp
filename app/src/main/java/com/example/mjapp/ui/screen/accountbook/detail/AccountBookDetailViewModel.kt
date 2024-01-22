package com.example.mjapp.ui.screen.accountbook.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.DateConfiguration
import com.example.network.model.NetworkError
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class AccountBookDetailViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val date = savedStateHandle.get<String>(NavScreen.AccountBookDetail.Date) ?: "2023.01.01"

    private val _info = mutableStateOf(AccountBookDetailInfo.init())
    val info: State<AccountBookDetailInfo> = _info

    private val _selectDate = mutableStateOf(date)
    val selectDate = _selectDate

    private val _calendarList = mutableStateListOf<AccountBookCalendar>()
    val calendarList: List<AccountBookCalendar> = _calendarList

    val itemList
        get() = _info.value.list.filter { it.date == _selectDate.value }

    init {
        fetchThisMonthDetail()
    }

    private fun fetchCalendar() {
        _calendarList.clear()
        _calendarList.addAll(
            createAccountBookCalendarList(
                startDate = _info.value.startDate,
                endDate = _info.value.endDate,
                list = _info.value.list
            )
        )
    }

    fun fetchThisMonthDetail() {
        repository
            .fetchThisMonthDetail(
                DateConfiguration.create(date = date, baseDate = 25)
            )
            .onStart { startLoading() }
            .onEach {
                _info.value = it.modifyDateFormat()
                fetchCalendar()
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState(true)
                else updateMessage(it.message ?: "오류가 발생하였습니다.")
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateSelectDate(date: String) {
        _selectDate.value = date
    }

}