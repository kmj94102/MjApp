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

    private val _number = mutableStateOf("")
    val number: State<String> = _number

    private val _index = mutableStateOf(0)
    val index: State<Int> = _index

    val item: PokemonSpotlightItem?
        get() = _list.getOrNull(_index.value)


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
        if (_index.value == 0) {
            _index.value = _list.lastIndex
        } else {
            _index.value = _index.value - 1
        }
        getNumber()
    }

    fun nextItem() {
        if (_index.value == _list.lastIndex) {
            _index.value = 0
        } else {
            _index.value = _index.value + 1
        }
        getNumber()
    }

    fun updateNumber(number: String) {
        _number.value = number
    }

    fun updateSpotlight() = viewModelScope.launch {
        _list.getOrNull(_index.value)?.let {
            val result = repository.updatePokemonSpotlight(
                item = PokemonSpotlightItem(
                    number = _number.value,
                    spotlight = getNewImage()
                )
            )
            _status.value = Status.Result(result)
        }
    }

    private fun getNumber() {
        _list.getOrNull(_index.value)?.let { info ->
            _number.value = info.number
        }
    }

    fun getNewImage() =
        "https://firebasestorage.googleapis.com/v0/b/mbank-2a250.appspot.com/o/${_number.value}.png?alt=media&token=d7d5689b-085f-4945-9ec6-6c61e94a4235"

    fun updateIndex(number: String) {
        val newIndex = _list.indexOfFirst { it.number == number }

        if (newIndex != -1) {
            _index.value = newIndex
        }
        getNumber()
    }

    sealed class Status {
        object Init : Status()
        data class Result(val msg: String) : Status()
    }

}