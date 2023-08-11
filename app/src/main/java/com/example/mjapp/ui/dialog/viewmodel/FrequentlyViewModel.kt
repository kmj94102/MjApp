package com.example.mjapp.ui.dialog.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.FrequentlyItem
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
class FrequentlyViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val _list = mutableStateListOf<FrequentlyItem>()
    val list: List<FrequentlyItem> = _list

    fun fetchFrequentlyAccountBook() {
        repository
            .fetchFrequentlyAccountBookItems()
            .onStart { startLoading() }
            .onEach { _list.clearAndAddAll(it) }
            .catch { updateNetworkErrorState(true) }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun deleteFrequently(id: Int) = viewModelScope.launch {
        repository
            .deleteFrequentlyAccountBookItem(id)
            .onSuccess { fetchFrequentlyAccountBook() }
            .onFailure { updateMessage("삭제를 실패하였습니다.") }
    }

}