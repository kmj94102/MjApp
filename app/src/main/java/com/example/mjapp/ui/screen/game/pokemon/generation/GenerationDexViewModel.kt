package com.example.mjapp.ui.screen.game.pokemon.generation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.GenerationCount
import com.example.network.model.NetworkError
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenerationDexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _list = mutableStateListOf<GenerationCount>()
    val list: List<GenerationCount> = _list

    init {
        fetchGenerationCountList()
    }

    fun fetchGenerationCountList() {
        repository
            .fetchGenerationCountList()
            .setLoadingState()
            .onEach { _list.clearAndAddAll(it) }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
            }
            .launchIn(viewModelScope)
    }

}