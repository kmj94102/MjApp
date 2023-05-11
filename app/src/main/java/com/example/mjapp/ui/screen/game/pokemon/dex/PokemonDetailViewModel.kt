package com.example.mjapp.ui.screen.game.pokemon.dex

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.UpdatePokemonCatch
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _info = mutableStateOf<PokemonDetailInfo?>(null)
    val info: State<PokemonDetailInfo?> = _info

    private val _status = mutableStateOf<Status>(Status.Init)
    val status: State<Status> = _status

    fun fetchPokemonDetail(number: String) {
        repository.fetchPokemonDetailInfo(number)
            .onStart { _isLoading.value = true }
            .onEach {
                _info.value = it
            }
            .catch {
                Log.e("PokemonDetailViewModel", it.message ?: "fetchPokemonDetail error")
                _info.value = null
            }
            .onCompletion { _isLoading.value = false }
            .launchIn(viewModelScope)
    }

    fun insertCounter() = viewModelScope.launch {
        val detailInfo = _info.value ?: return@launch

        try {
            repository.insertPokemonCounter(detailInfo)
            _status.value = Status.InsertCounterSuccess
        } catch (e: Exception) {
            _status.value = Status.Error(e.message ?: "카운터 등록을 실패하였습니다.")
        }
    }

    fun updateCatch() = viewModelScope.launch {
        val info = _info.value?.pokemonInfo ?: return@launch
        val result = repository.updatePokemonCatch(
            UpdatePokemonCatch(
                number = info.number,
                isCatch = info.isCatch.not()
            )
        )

        if (result.contains("완료")) {
            _info.value = _info.value?.copy(
                pokemonInfo = info.copy(
                    isCatch = info.isCatch.not()
                )
            )
        } else {
            _status.value = Status.Error(result)
        }
    }

    fun statusReset() {
        _status.value = Status.Init
    }

    sealed class Status {
        object Init: Status()

        data class Error(
            val msg: String
        ): Status()

        object InsertCounterSuccess: Status()
    }

}