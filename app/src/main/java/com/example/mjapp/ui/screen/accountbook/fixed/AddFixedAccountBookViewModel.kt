package com.example.mjapp.ui.screen.accountbook.fixed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.removeNumberFormat
import com.example.network.model.FixedAccountBook
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFixedAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val _item = mutableStateOf(FixedAccountBook.create())
    val item: State<FixedAccountBook> = _item

    fun updateIsIncome(value: Boolean) {
        _item.value = _item.value.copy(isIncome = value)
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

    fun updateWhereToUse(value: String) {
        _item.value = _item.value.copy(whereToUse = value)
    }

    fun updateUsageType(value: String) {
        _item.value = _item.value.copy(usageType = value)
    }

    fun updateDay(value: String) {
        _item.value = _item.value.copy(date = value)
    }

    fun insertFixedAccountBookItem() = viewModelScope.launch {
        val i = if (_item.value.isIncome) 1 else -1
        val item = _item.value.copy(amount = _item.value.amount * i)

        repository
            .insertFixedAccountBookItem(item)
            .onSuccess {
                updateMessage(it)
                updateFinish()
            }
            .onFailure {
                updateMessage(it.message ?: "등록에 실패하였습니다.")
            }
    }

}