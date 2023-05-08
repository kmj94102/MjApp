package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.PokemonSummary
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var skip = 0

    private val _pokemonList = mutableStateListOf<PokemonSummary>()
    val pokemonList: List<PokemonSummary> = _pokemonList

    private val _selectNumber = mutableStateOf("")
    val selectNumber: State<String> = _selectNumber

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isShiny = mutableStateOf(false)
    val isShiny: State<Boolean> = _isShiny

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        repository
            .fetchPokemonList(
                skip = skip
            )
            .onStart {
                _isLoading.value = true
            }
            .onEach {
                _pokemonList.addAll(it.list)
                skip = it.list.last().index
            }
            .catch {
            }
            .onCompletion {
                _isLoading.value = false
            }
            .launchIn(viewModelScope)
    }

    fun toggleShinyState() {
        _isShiny.value = _isShiny.value.not()
    }

    fun updateSelectNumber(number: String) {
        _selectNumber.value = number
    }

}