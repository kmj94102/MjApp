package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

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

    fun updateCounter(value: Int, number: String) = viewModelScope.launch {
        repository.updateCounter(value, number)
    }

    fun updateCustomIncrease(customIncrease: Int, number: String) = viewModelScope.launch {
        repository.updateCustomIncrease(customIncrease, number)
    }

    fun deleteCounter(number: String) = viewModelScope.launch {
        repository.deletePokemonCounter(number)
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