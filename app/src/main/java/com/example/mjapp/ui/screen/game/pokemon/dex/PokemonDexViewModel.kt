package com.example.mjapp.ui.screen.game.pokemon.dex

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.game.pokemon.search.PokemonSearchItem
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.NetworkError
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : BaseViewModel() {

    private val _state = mutableStateOf(PokemonDexState())
    val state: State<PokemonDexState> = _state

    private val filterInfo = mutableStateOf(PokemonSearchItem())

    private var page = 0
    private val limit = 100

    init {
        fetchPokemonDex()
    }

    fun updateFilterInfo(item: PokemonSearchItem) {
        if (filterInfo.value == item) return

        filterInfo.update {
            it.copy(
                name = item.name,
                registrationStatus = item.registrationStatus,
                generation = item.generation,
                type = item.type,
            )
        }
        page = 0
        _state.value = PokemonDexState()
        fetchPokemonDex()
    }

    fun fetchPokemonDex() {
        Log.e("+++++", "${filterInfo.value}")
        repository
            .fetchPokemonList(
                name = filterInfo.value.name.trim(),
                skip = page,
                limit = limit,
                generations = filterInfo.value.generation.joinToString(","),
                types = filterInfo.value.type.joinToString(","),
                isCatch = filterInfo.value.registrationStatus
            )
            .setLoadingState()
            .onEach { (isMoreDate, list) ->
                _state.value = _state.value.copy(
                    isMoreDate = isMoreDate,
                    list = _state.value.list + list
                )
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
            }
            .launchIn(viewModelScope)
    }

    fun fetchMoreData(index: Int) {
        if (_state.value.isMoreDate.not()) return

        if (index >= 20 + (page * limit)) {
            page += 1
            fetchPokemonDex()
        }
    }

    fun getSearchInfo() = NavScreen2.PokemonSearch(
        name =  filterInfo.value.name,
        types = filterInfo.value.type,
        generations = filterInfo.value.generation,
        registrations = filterInfo.value.registrationStatus
    )

    fun getFilterList() = filterInfo.value.getFilterItems()

}