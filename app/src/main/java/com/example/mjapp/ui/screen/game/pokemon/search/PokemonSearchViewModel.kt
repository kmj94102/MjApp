package com.example.mjapp.ui.screen.game.pokemon.search

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.GenerationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): BaseViewModel(){

    private val _state = MutableStateFlow(
        savedStateHandle.toRoute<NavScreen2.PokemonSearch>()
    )
    val state: StateFlow<NavScreen2.PokemonSearch> = _state

    fun updateName(name: String){
        _state.update { it.copy(name = name) }
    }

    fun updateType(type: String){
        val newList = _state.value.types.toMutableList()
        when {
            type == NavScreen2.PokemonSearch.ALL -> newList.clear()
            newList.contains(type) -> newList.remove(type)
            newList.size < 2 -> newList.add(type)
            else -> updateMessage("타입은 최대 2개까지 선택 가능합니다.")
        }

        _state.update { it.copy(types = newList) }
    }

    fun updateGeneration(type: String){
        val newList = _state.value.generations.toMutableList()
        when {
            type == GenerationInfo.GENERATION_ALL.generation.toString() -> newList.clear()
            newList.contains(type) -> newList.remove(type)
            newList.size >= GenerationInfo.entries.size - 2 -> newList.clear()
            else -> newList.add(type)
        }
        newList.sort()

        _state.update { it.copy(generations = newList) }
    }

    fun updateRegistrationStatus(registrations: String){
        _state.update { it.copy(registrations = registrations) }
    }

    fun clear() {
        _state.update { NavScreen2.PokemonSearch() }
    }

}