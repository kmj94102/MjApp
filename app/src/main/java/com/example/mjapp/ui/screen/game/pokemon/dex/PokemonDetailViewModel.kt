package com.example.mjapp.ui.screen.game.pokemon.dex

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.PokemonDetailInfo
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _info = mutableStateOf<PokemonDetailInfo?>(null)
    val info: State<PokemonDetailInfo?> = _info

    fun fetchPokemonDetail(number: String) {
        repository.fetchPokemonDetailInfo(number)
            .onStart { _isLoading.value = true }
            .onEach {
                _info.value = it
            }
            .catch {
                Log.e("PokemonDetailViewModel", it.message ?: "fetchPokemonDetail error")
                _info.value = null
            }
            .onCompletion { _isLoading.value = false }
            .launchIn(viewModelScope)
    }

}