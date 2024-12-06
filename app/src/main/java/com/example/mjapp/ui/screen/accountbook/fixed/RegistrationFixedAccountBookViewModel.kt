package com.example.mjapp.ui.screen.accountbook.fixed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.mjapp.util.clearAndAddAll
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.removeNumberFormat
import com.example.network.model.FixedAccountBook
import com.example.network.model.NetworkError
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationFixedAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _list = mutableStateListOf<FixedAccountBook>()
    val list: List<FixedAccountBook> = _list

    private val _yearMonth =
        mutableStateOf(savedStateHandle.get<String>(Constants.DATE) ?: "2023.01")
    val yearMonth: State<String> = _yearMonth
    val year: String
        get() = runCatching { _yearMonth.value.substring(0, 4) }.getOrElse { "2023" }

    val month: String
        get() = runCatching { _yearMonth.value.substring(5, 7) }.getOrElse { "01" }

    init {
        fetchFixedAccountBook()
    }

    fun fetchFixedAccountBook() {
        repository
            .fetchFixedAccountBook()
            .onStart { startLoading() }
            .onEach { _list.clearAndAddAll(it) }
            .catch {
                if (it is NetworkError) {
                    updateNetworkErrorState()
                } else {
                    _list.clear()
                    updateMessage(it.message ?: "정보를 불러오는 도중 실패하였습니다.")
                }
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun registrationItem(item: FixedAccountBook) = viewModelScope.launch {
        repository
            .insertNewAccountBookItem(
                item = item.toAccountBookInsertItem(yearMonth = _yearMonth.value),
                isIncome = item.isIncome
            )
            .onSuccess { updateMessage(it) }
            .onFailure { updateMessage(it.message ?: "등록을 실패하였습니다.") }
    }

    fun deleteItem(item: FixedAccountBook) = viewModelScope.launch {
        repository
            .deleteAccountBookItem(item.id)
            .onSuccess { fetchFixedAccountBook() }
            .onFailure { updateMessage("삭제를 실패하였습니다.") }
    }

    fun updateAmount(amount: String, index: Int) {
        val value = amount.removeNumberFormat()
        if (isNumeric(value)) {
            _list[index] = _list[index].copy(
                amount = runCatching {
                    if (_list[index].amount == value.toInt()) {
                        0
                    } else {
                        value.toInt()
                    }
                }.getOrElse {
                    Int.MAX_VALUE
                }
            )
        }
    }

    fun updateDay(day: String, index: Int) = runCatching {
        _list[index] = _list[index].copy(date = day)
    }

    fun updateYearMonth(year: String, month: String) {
        _yearMonth.value = "$year.$month"
    }

}