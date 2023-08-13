package com.example.mjapp.ui.dialog.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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
class UsageHistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val _info = mutableStateOf(AccountBookDetailInfo.init())
    val info: State<AccountBookDetailInfo> = _info

    fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) {
        repository
            .fetchThisMonthDetail(dateConfiguration)
            .onStart { startLoading() }
            .onEach { _info.value = it }
            .catch {
                if (it is NetworkError) updateNetworkErrorState(true)
                else updateMessage("오류가 발생하였습니다.")
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

}