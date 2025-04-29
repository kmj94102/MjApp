package com.example.mjapp.ui.screen.game.pokemon.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.UpdatePokemonCatch
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : BaseViewModel() {
    private val _isShiny = mutableStateOf(false)
    val isShiny: State<Boolean> = _isShiny

    private val number = MutableStateFlow<String>(
        savedStateHandle.toRoute<NavScreen2.PokemonDetail>().number
    )
    private val _pokemon = MutableStateFlow<PokemonDetailInfo>(PokemonDetailInfo.init())
    val pokemon: StateFlow<PokemonDetailInfo> = _pokemon.asStateFlow()

    init {
        number
            .flatMapLatest {
                repository.fetchPokemonDetailInfo(it)
            }
            .onEach { _pokemon.value = it }
            .launchIn(viewModelScope)
    }

    fun updateNumber(value: String) {
        number.value = value
    }

    fun updateIsShiny() {
        _isShiny.value = _isShiny.value.not()
    }

    fun updateIsCatch() {
        val newStatus = _pokemon.value.pokemonInfo.isCatch.not()
        repository
            .updatePokemonCatch(UpdatePokemonCatch(number.value, newStatus))
            .onEach {
                _pokemon.update { it.copy(pokemonInfo = it.pokemonInfo.copy(isCatch = newStatus)) }
            }
            .catch { updateMessage("업데이트 실패") }
            .launchIn(viewModelScope)
    }

    fun insertPokemonCounter() {
        repository
            .insertPokemonCounter(_pokemon.value)
            .onEach { updateMessage("카운터 등록 완료") }
            .catch { updateMessage("카운터 등록 실패") }
            .launchIn(viewModelScope)
    }
}