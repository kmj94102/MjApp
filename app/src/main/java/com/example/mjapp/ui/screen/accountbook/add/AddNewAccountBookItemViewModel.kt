package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.removeNumberFormat
import com.example.network.model.AccountBookInsertItem
import com.example.network.model.FixedItem
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAccountBookItemViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _item = mutableStateOf(AccountBookInsertItem.initItem())
    val item: State<AccountBookInsertItem> = _item

    private val _isIncome = mutableStateOf(true)
    val isIncome: State<Boolean> = _isIncome

    init {
        savedStateHandle.get<String>(NavScreen.AddNewAccountBookItem.DATE)?.let {
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

    fun updateIsAddFrequently() {
        _item.value = _item.value.copy(
            isAddFrequently = _item.value.isAddFrequently.not()
        )
    }

    fun insertNewAccountBook() = viewModelScope.launch {
        val check = _item.value.checkValidity()
        if (check.isNotEmpty()) {
            updateMessage(check)
            return@launch
        }

        repository
            .insertNewAccountBookItem(
                item = _item.value,
                isIncome = _isIncome.value
            )
            .onSuccess {
                updateMessage(it)
                updateFinish()
            }
            .onFailure {
                it.printStackTrace()
                updateMessage("등록 실패")
            }
    }

    fun updateIsIncome(isIncome: Boolean) {
        _isIncome.value = isIncome
    }

    fun updateWithFixedItem(item: FixedItem) {
        _item.value = _item.value.copy(
            amount = item.amount,
            usageType = item.usageType,
            whereToUse = item.whereToUse,
            isAddFrequently = false
        )
        _isIncome.value = item.amount > 0
    }

}
