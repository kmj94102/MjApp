package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.PokemonCounter
import com.example.network.model.UpdatePokemonCatch
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonCounterViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _list = mutableStateListOf<PokemonCounter>()
    val list: List<PokemonCounter> = _list

    init {
        fetchPokemonCounter()
    }

    private fun fetchPokemonCounter() {
        repository
            .fetchPokemonCounter()
            .onEach {
                _list.clear()
                _list.addAll(it)
            }
            .catch {
                _list.clear()
            }
            .launchIn(viewModelScope)
    }

    fun insertPokemonCounter(number: String) = viewModelScope.launch {
        repository.insertPokemonCounter(number)
    }

    fun updateCounter(value: Int, number: String) = viewModelScope.launch {
        repository.updateCounter(value, number)
    }

    fun updateCustomIncrease(customIncrease: Int, number: String) = viewModelScope.launch {
        repository.updateCustomIncrease(customIncrease, number)
    }

    fun deleteCounter(index: Int) = viewModelScope.launch {
        repository.deletePokemonCounter(index)
    }

    fun updateCatch(number: String) = viewModelScope.launch {
        repository.updateCatch(number)
        repository.updatePokemonCatch(
            UpdatePokemonCatch(
                number = number,
                isCatch = true
            )
        )
    }
}