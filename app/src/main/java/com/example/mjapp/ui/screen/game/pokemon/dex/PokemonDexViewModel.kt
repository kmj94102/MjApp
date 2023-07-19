package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.PokemonSummary
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    private val _list = mutableStateListOf<PokemonSummary>()
    val list: List<PokemonSummary> = _list

    private val _isMoreDate = mutableStateOf(true)
    val isMoreDate: State<Boolean> = _isMoreDate

    private var page = 0
    private val limit = 100

    init {
        fetchPokemonDex()
    }

    private fun fetchPokemonDex() = viewModelScope.launch {
        repository.fetchPokemonList(
            name = _search.value.trim(),
            skip = page,
            limit = limit
        ) { isMoreDate, list ->
            _isMoreDate.value = isMoreDate
            _list.addAll(list)
        }
    }

    fun fetchMoreData(index: Int) {
        if (index >= 20 + (page * limit)) {
            page += 1
            fetchPokemonDex()
        }
    }

    fun toggleShinyState() {
        _isShiny.value = _isShiny.value.not()
    }

    fun updateSelectNumber(number: String) {
        _selectNumber.value = number
    }

    fun updateSearchValue(value: String) {
        _search.value = value
        _list.clear()
        page = 0
        _isMoreDate.value = true
        fetchPokemonDex()
    }

    fun updateCatchState(isCatch: Boolean) {
        val index = _list.indexOfFirst {
            it.number == _selectNumber.value
        }
        if (index == -1) return
        _list[index] = _list[index].copy(isCatch = isCatch)
    }

}