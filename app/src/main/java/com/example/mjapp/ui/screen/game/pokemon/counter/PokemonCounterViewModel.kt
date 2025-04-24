package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class PokemonCounterViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _state = mutableStateOf(PokemonCounterState())
    val state: State<PokemonCounterState> = _state

    init {
        fetchPokemonCounter()
    }

    private fun fetchPokemonCounter() {
        repository
            .fetchPokemonCounter()
            .onEach { list ->
                _state.update { it.copy(list = list) }
            }
            .catch { _state.update { it.copy(list = emptyList()) } }
            .launchIn(viewModelScope)
    }

    fun updateSelectIndex(index: Int) {
        _state.value = _state.value.copy(selectIndex = index)
    }

    fun updateCounter(value: Int) = viewModelScope.launch {
        val pokemon = _state.value.getSelectPokemon() ?: return@launch
        val count = max(pokemon.count + value, 0)

        repository.updateCounter(count, pokemon.number)
    }

    fun deleteCounter() = viewModelScope.launch {
        val pokemon = _state.value.getSelectPokemon() ?: return@launch
        repository.deletePokemonCounter(pokemon.index)
    }

    fun updateCatch() = viewModelScope.launch {
        val pokemon = _state.value.getSelectPokemon() ?: return@launch
        repository.updateCatch(pokemon.number)
    }
}