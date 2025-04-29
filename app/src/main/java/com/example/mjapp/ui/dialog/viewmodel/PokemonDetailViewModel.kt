package com.example.mjapp.ui.dialog.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.UpdatePokemonCatch
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _info = mutableStateOf<PokemonDetailInfo?>(null)
    val info: State<PokemonDetailInfo?> = _info

    private val _isShiny = mutableStateOf(false)
    val isShiny: State<Boolean> = _isShiny

    fun fetchPokemonDetail(number: String) {
        repository.fetchPokemonDetailInfo(number)
            .onStart { startLoading() }
            .onEach { _info.value = it }
            .catch { _info.value = null }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun insertCounter() = viewModelScope.launch {
        val detailInfo = _info.value ?: return@launch

//        repository
//            .insertPokemonCounter(detailInfo)
//            .onSuccess {
//                updateMessage("${detailInfo.pokemonInfo.name} 등록이 완료되었습니다.")
//            }
//            .onFailure {
//                it.printStackTrace()
//                updateMessage("카운터 등록을 실패하였습니다.")
//            }
    }

    fun updateCatch() = viewModelScope.launch {
        val info = _info.value?.pokemonInfo ?: return@launch
        val item = UpdatePokemonCatch(
            number = info.number,
            isCatch = info.isCatch.not()
        )

//        repository
//            .updatePokemonCatch(item)
//            .onSuccess {
//                _info.value =
//                    _info.value?.copy(pokemonInfo = info.copy(isCatch = info.isCatch.not()))
//                updateFinish()
//            }
//            .onFailure {
//                it.printStackTrace()
//                updateMessage("업데이트 실패")
//            }
    }

    fun toggleIsShiny() {
        _isShiny.value = _isShiny.value.not()
    }

}