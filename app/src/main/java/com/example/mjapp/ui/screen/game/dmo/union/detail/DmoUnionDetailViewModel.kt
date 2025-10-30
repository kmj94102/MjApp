package com.example.mjapp.ui.screen.game.dmo.union.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.DmoUnionDetail
import com.example.network.model.NetworkError
import com.example.network.repository.DigimonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DmoUnionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DigimonRepository
): BaseViewModel() {
    private val id = savedStateHandle.toRoute<NavScreen2.DmoUnionDetail>().id
    private val _info = mutableStateOf(DmoUnionDetail())
    val info: State<DmoUnionDetail> = _info

    init { fetchUnionDetail() }

    fun fetchUnionDetail() {
        repository
            .fetchUnionDetail(id)
            .setLoadingState()
            .onEach { _info.value = it }
            .catch {
                it.printStackTrace()
                if (it is NetworkError) updateNetworkErrorState()
                else updateMessage("유니온 상세 조회 실패")
            }
            .launchIn(viewModelScope)
    }
}