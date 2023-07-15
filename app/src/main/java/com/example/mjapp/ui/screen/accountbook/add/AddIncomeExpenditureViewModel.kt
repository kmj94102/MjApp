package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.removeNumberFormat
import com.example.network.model.AccountBookItem
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAccountBookItemViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _item = mutableStateOf(AccountBookItem.initItem())
    val item: State<AccountBookItem> = _item

    private val _status = mutableStateOf<Status>(Status.Init)
    val status: State<Status> = _status

    private val _isIncome = mutableStateOf(true)
    val isIncome: State<Boolean> = _isIncome

    init {
        savedStateHandle.get<String>(NavScreen.AddNewAccountBookItem.Date)?.let {
            updateDateInfo(it)
        }
    }

    fun updateDateInfo(date: String) {
        _item.value = _item.value.updateDate(date)
    }

    fun updateAmount(amount: String) {
        val value = amount.removeNumberFormat()
        if (isNumeric(value)) {
            _item.value = _item.value.copy(
                amount = runCatching {
                    if (_item.value.amount == value.toInt()) {
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

    fun updateUsageType(usageType: String) {
        _item.value = _item.value.copy(
            usageType = usageType
        )
    }

    fun updateWhereToUse(whereToUse: String) {
        _item.value = _item.value.copy(
            whereToUse = whereToUse
        )
    }

    fun insertNewAccountBook() = viewModelScope.launch {
        val check = _item.value.checkValidity()
        if (check.isNotEmpty()) {
            _status.value = Status.Failure(check)
            return@launch
        }

        repository
            .insertNewAccountBookItem(
                item = _item.value,
                isIncome = _isIncome.value
            )
            .onSuccess {
                _status.value = Status.Success(it)
            }
            .onFailure {
                it.printStackTrace()
                _status.value = Status.Failure("등록 실패")
            }
    }

    fun updateIsIncome(isIncome: Boolean) {
        _isIncome.value = isIncome
    }

    fun updateStateInit() {
        _status.value = Status.Init
    }

    sealed class Status {
        object Init: Status()

        data class Success(
            val msg: String
        ): Status()

        data class Failure(
            val msg: String
        ): Status()
    }

}
