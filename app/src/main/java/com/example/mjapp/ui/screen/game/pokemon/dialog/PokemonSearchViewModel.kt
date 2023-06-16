package com.example.mjapp.ui.screen.game.pokemon.dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.BriefPokemonItem
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    private val _list = mutableStateListOf<BriefPokemonItem>()
    val list: List<BriefPokemonItem> = _list

    private val _selectItem = mutableStateOf<BriefPokemonItem?>(null)
    val selectItem: State<BriefPokemonItem?> = _selectItem

    fun updateSearch(search: String) {
        _search.value = search
    }

    fun searchPokemonList() {
        repository
            .fetchBriefPokemonList(search = _search.value)
            .onEach {
                _list.clear()
                _list.addAll(it)
            }
            .launchIn(viewModelScope)
    }

    fun updateSelectItem(index: Int) {
        _list.getOrNull(index)?.let {
            _selectItem.value = it
        }
    }

}