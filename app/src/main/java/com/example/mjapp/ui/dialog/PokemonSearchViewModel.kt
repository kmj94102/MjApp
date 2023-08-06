package com.example.mjapp.ui.dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.BriefPokemonItem
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
class PokemonSearchViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    private val _list = mutableStateListOf<BriefPokemonItem>()
    val list: List<BriefPokemonItem> = _list

    private val _selectItem = mutableStateOf<BriefPokemonItem?>(null)
    val selectItem: State<BriefPokemonItem?> = _selectItem

    fun updateSearch(search: String) {
        _search.value = search
    }

    fun searchPokemonList(value: String) {
        repository
            .fetchBriefPokemonList(search = value.trim())
            .onStart { startLoading() }
            .onEach {
                _list.clearAndAddAll(it)
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
            }
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    fun updateSelectItem(index: Int) {
        _list.getOrNull(index)?.let {
            _selectItem.value = it
        }
    }

}