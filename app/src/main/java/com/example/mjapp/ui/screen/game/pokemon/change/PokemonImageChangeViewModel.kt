package com.example.mjapp.ui.screen.game.pokemon.change

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.PokemonSpotlightItem
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonImageChangeViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _list = mutableStateListOf<PokemonSpotlightItem>()

    private val _state = mutableStateOf(PokemonImageChangeState())
    val state: State<PokemonImageChangeState> = _state

    val item: PokemonSpotlightItem?
        get() = _list.getOrNull(_state.value.index)


    private val _status = mutableStateOf<Status>(Status.Init)
    val status: State<Status> = _status

    init {
        fetchBeforeSpotlight()
    }

    private fun fetchBeforeSpotlight() {
        repository
            .fetchPokemonBeforeSpotlights()
            .onEach {
                _list.clear()
                _list.addAll(it)
                getNumber()
            }
            .launchIn(viewModelScope)
    }

    fun prevItem() {
        val value = _state.value
        val newIndex = if (value.index == 0) {
            _list.lastIndex
        } else {
           value.index - 1
        }
        _state.value = value.copy(index = newIndex)

        getNumber()
    }

    fun nextItem() {
        val value = _state.value
        val newIndex = if (value.index == _list.lastIndex) {
            0
        } else {
            value.index + 1
        }
        _state.value = value.copy(index = newIndex)

        getNumber()
    }

    fun updateNumber(number: String) {
        _state.value = _state.value.copy(number = number)
    }

    fun updateSpotlight() = viewModelScope.launch {
        _list.getOrNull(_state.value.index)?.let {
            val result = repository.updatePokemonSpotlight(
                item = PokemonSpotlightItem(
                    number = _state.value.number,
                    spotlight = getNewImage()
                )
            )
            _status.value = Status.Result(result)
        }
    }

    private fun getNumber() {
        _list.getOrNull(_state.value.index)?.let { info ->
            _state.value = _state.value.copy(number = info.number)
        }
    }

    fun getNewImage() =
        "https://firebasestorage.googleapis.com/v0/b/mbank-2a250.appspot.com/o/${_state.value.number}.png?alt=media&token=d7d5689b-085f-4945-9ec6-6c61e94a4235"

    fun updateIndex(number: String) {
        val newIndex = _list.indexOfFirst { it.number == number }

        if (newIndex != -1) {
            _state.value = _state.value.copy(index = newIndex)
        }
        getNumber()
    }

    sealed class Status {
        object Init : Status()
        data class Result(val msg: String) : Status()
    }

}