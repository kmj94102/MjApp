package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _selectNumber = mutableStateOf("")
    val selectNumber: State<String> = _selectNumber

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isShiny = mutableStateOf(false)
    val isShiny: State<Boolean> = _isShiny

    fun fetchPokemonDex() = repository
        .fetchPokemonList()
        .cachedIn(viewModelScope)

    fun toggleShinyState() {
        _isShiny.value = _isShiny.value.not()
    }

    fun updateSelectNumber(number: String) {
        _selectNumber.value = number
    }

}