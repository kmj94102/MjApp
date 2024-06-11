package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.NetworkError
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

    private val _state = mutableStateOf(PokemonDexState())
    val state: State<PokemonDexState> = _state

    private var page = 0
    private val limit = 100

    init {
        fetchPokemonDex()
    }

    fun fetchPokemonDex() {
        repository
            .fetchPokemonList(
                name = _state.value.search.trim(),
                skip = page,
                limit = limit
            )
            .onStart { startLoading() }
            .onEach { (isMoreDate, list) ->
                _state.value = _state.value.copy(
                    isMoreDate = isMoreDate,
                    list = _state.value.list + list
                )
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
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
        val value = _state.value
        _state.value = value.copy(isShiny = value.isShiny.not())
    }

    fun updateSelectNumber(number: String) {
        _state.value = _state.value.copy(selectNumber = number)
    }

    fun updateSearchValue(value: String) {
        page = 0
        _state.value = _state.value.copy(
            search = value,
            list = listOf(),
            isMoreDate = true
        )
        fetchPokemonDex()
    }

    fun updateCatchState(isCatch: Boolean) {
        val value = _state.value
        val result = value.list.map {
            if (it.number == value.selectNumber) {
                it.copy(isCatch = isCatch)
            } else it
        }
        _state.value = _state.value.copy(list = result)
    }

}