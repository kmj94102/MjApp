package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.PokemonCounter
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonCounterHistoryViewModel @Inject constructor(
    private val repository: PokemonRepository
): BaseViewModel() {

    private val _list = mutableStateListOf<PokemonCounter>()
    val list: List<PokemonCounter> = _list

    init {
        fetchPokemonCounterHistory()
    }

    private fun fetchPokemonCounterHistory() {
        repository
            .fetchPokemonCounterHistory()
            .onEach {
                _list.clearAndAddAll(it)
            }
            .catch {
                _list.clear()
            }
            .launchIn(viewModelScope)
    }

    fun deleteCounter(index: Int) = viewModelScope.launch {
        repository
            .deletePokemonCounter(index)
    }

    fun updateRestore(index: Int, number: String) = viewModelScope.launch {
        repository
            .updateRestore(index, number)
    }

}