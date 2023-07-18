package com.example.mjapp.ui.screen.accountbook

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.util.getToday
import com.example.network.model.DateConfiguration
import com.example.network.model.SummaryAccountBookThisMonthInfo
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
): ViewModel() {

    private val _info = mutableStateOf<SummaryAccountBookThisMonthInfo?>(null)
    val info: State<SummaryAccountBookThisMonthInfo?> = _info

    init {
        fetchSummaryThisMonth()
    }

    private fun fetchSummaryThisMonth() = viewModelScope.launch {
        val config = DateConfiguration.create(
            date = getToday(),
            baseDate = 25
        )

        repository
            .fetchSummaryThisMonth(config)
            .onSuccess {
                _info.value = it
            }
            .onFailure {
                it.printStackTrace()
                _info.value = null
            }
    }

}