package com.example.mjapp.ui.screen.accountbook

import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.getToday
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.DateConfiguration
import com.example.network.model.NetworkError
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountBookViewModel @Inject constructor(
    val repository: AccountBookRepository
) : BaseViewModel() {
    val info = repository
        .fetchThisMonthDetail(
            DateConfiguration.create(
                date = getToday(),
                baseDate = 25
            )
        )
        .catch {
            if (it is NetworkError) {
                updateNetworkErrorState()
            } else {
                updateMessage(it.message ?: "??")
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, AccountBookDetailInfo.init())
}