package com.example.mjapp.ui.screen.game.pokemon.counter.history

import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonCounterHistoryViewModel @Inject constructor(
    private val repository: PokemonRepository
): BaseViewModel() {
    val list = repository
        .fetchPokemonCounterHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun deleteCounter(index: Int) = viewModelScope.launch {
        repository
            .deletePokemonCounter(index)
    }

    fun updateRestore(index: Int, number: String) = viewModelScope.launch {
        repository
            .updateRestore(index, number)
    }

}