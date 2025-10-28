package com.example.mjapp.ui.screen.game.dmo.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.DmoUnionInfo
import com.example.network.model.NetworkError
import com.example.network.repository.DigimonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DmoUnionViewModel @Inject constructor(
    private val repository: DigimonRepository
): BaseViewModel() {

    private val _list = mutableStateListOf<DmoUnionInfo>()
    val list: List<DmoUnionInfo> = _list

    init {
        fetchUnionList()
    }

    fun fetchUnionList() {
        repository
            .fetchUnionList()
            .setLoadingState()
            .onEach {
                _list.clear()
                _list.addAll(it)
            }
            .catch {
                if (it is NetworkError) {
                    updateNetworkErrorState()
                } else {
                    updateMessage(it.message ?: "??")
                }
            }
            .launchIn(viewModelScope)
    }
}