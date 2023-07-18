package com.example.mjapp.ui.screen.accountbook.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.DateConfiguration
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountBookDetailViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val date = savedStateHandle.get<String>(NavScreen.AccountBookDetail.Date) ?: "2023.01.01"

    private val _info = mutableStateOf(AccountBookDetailInfo.init())
    val info: State<AccountBookDetailInfo> = _info

    private val _selectDate = mutableStateOf(date)
    val selectDate = _selectDate

    val itemList
        get() = _info.value.list.filter { it.date == _selectDate.value }

    init {
        fetchThisMonthDetail()
    }

    private fun fetchThisMonthDetail() = viewModelScope.launch {
        repository
            .fetchThisMonthDetail(
                DateConfiguration(
                    date = "${date.replace(".", "-")}T10:00:00.000Z",
                    baseDate = 25
                )
            )
            .onSuccess {
                _info.value = it.modifyDateFormat()
            }
    }

    fun updateSelectDate(date: String) {
        _selectDate.value = date
    }

}