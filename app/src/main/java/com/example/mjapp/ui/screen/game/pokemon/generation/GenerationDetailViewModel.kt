package com.example.mjapp.ui.screen.game.pokemon.generation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.Constants
import com.example.network.model.GenerationUpdateParam
import com.example.network.model.NetworkError
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenerationDetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = mutableStateOf(GenerationDetailState())
    val state: State<GenerationDetailState> = _state

    init {
        fetchGenerationList()
    }

    fun fetchGenerationList() {
        val index = savedStateHandle.get<String>(Constants.INDEX)?.toIntOrNull()
        if (index == null) {
            updateFinish()
            return
        }

        repository
            .fetchGenerationList(index)
            .setLoadingState()
            .onEach {
                _state.value = _state.value.copy(
                    list = it
                )
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
            }
            .launchIn(viewModelScope)
    }

    fun setSelectPokemon(
        number: String,
        idx: Int,
        isCatch: Boolean
    ) {
        _state.value = _state.value.copy(
            selectNumber = number,
            selectIdx = idx,
            isCatch = isCatch,
            isDialogShow = true
        )
    }

    fun dismissDialog() {
        _state.value = _state.value.copy(
            isDialogShow = false
        )
    }

    fun updateCatch() {
        repository
            .updateGenerationIsCatch(GenerationUpdateParam(_state.value.selectIdx))
            .setLoadingState()
            .onEach {
                val newList = _state.value.list.map { item ->
                    Log.e("+++++", "${item.idx} / ${_state.value.selectIdx}")
                    if(item.generationIdx == _state.value.selectIdx) {
                        item.copy(isCatch = it)
                    } else {
                        item
                    }
                }

                _state.value = _state.value.copy(
                    isCatch = it,
                    list = newList
                )
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
            }
            .launchIn(viewModelScope)
    }

}