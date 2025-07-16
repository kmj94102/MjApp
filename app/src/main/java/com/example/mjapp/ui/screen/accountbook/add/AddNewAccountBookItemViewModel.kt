package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.removeNumberFormat
import com.example.mjapp.util.update
import com.example.network.model.AccountBookInsertItem
import com.example.network.model.FixedAccountBook
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class AddNewAccountBookItemViewModel @Inject constructor(
    private val repository: AccountBookRepository
): BaseViewModel() {
    private val _item = mutableStateOf(AccountBookInsertItem.initItem())
    val item: State<AccountBookInsertItem> = _item

    fun updateDateInfo(date: String) {
        _item.update { it.updateDate(date) }
    }

    fun updateAmount(amount: String) {
        val value = amount.removeNumberFormat()
        if (isNumeric(value)) {
            _item.update {
                it.copy(
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
    }

    fun updateUsageType(usageType: String) {
        _item.update { it.copy(usageType = usageType) }
    }

    fun updateWhereToUse(whereToUse: String) {
        _item.update { it.copy(whereToUse = whereToUse) }
    }

    fun updateIsAddFrequently() {
        _item.update { it.copy(isAddFrequently = it.isAddFrequently.not()) }
    }

    fun insertNewAccountBook() = viewModelScope.launch {
        val check = _item.value.checkValidity()
        if (check.isNotEmpty()) {
            updateMessage(check)
            return@launch
        }

        repository
            .insertNewAccountBookItem(item = _item.value)
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
        _item.update { it.copy(isIncome = isIncome) }
    }

    fun updateWithFixedItem(item: FixedAccountBook) {
        _item.update {
            it.copy(
                amount = abs(item.amount),
                usageType = item.usageType,
                whereToUse = item.whereToUse,
                isAddFrequently = false,
                isIncome = item.amount > 0
            )
        }
    }

}
