package com.example.mjapp.ui.screen.accountbook

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getToday
import com.example.network.model.AccountBookMainInfo
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
class AccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val _info = mutableStateOf<AccountBookMainInfo?>(null)
    val info: State<AccountBookMainInfo?> = _info

    init {
        fetchSummaryThisMonth()
    }

    fun fetchSummaryThisMonth() {
        val config = DateConfiguration.create(
            date = getToday(),
            baseDate = 25
        )

        repository
            .fetchAccountBookInfo(config)
            .onStart { startLoading() }
            .onEach {
                _info.value = it
            }
            .catch {
                if (it is NetworkError) {
                    updateNetworkErrorState()
                } else {
                    updateMessage(it.message ?: "??")
                }
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

}