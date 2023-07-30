package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.NetworkError
import com.example.network.model.PokemonSummary
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _selectNumber = mutableStateOf("")
    val selectNumber: State<String> = _selectNumber

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

    fun fetchPokemonDex() {
        repository
            .fetchPokemonList(
                name = _search.value.trim(),
                skip = page,
                limit = limit
            )
            .onStart { startLoading() }
            .onEach { (isMoreDate, list) ->
                _isMoreDate.value = isMoreDate
                _list.addAll(list)
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState(true)
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
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