package com.example.mjapp.ui.screen.game.pokemon.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.util.isNumeric
import com.example.network.model.PokemonInfo
import com.example.network.repository.ExternalRepository
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonAddViewModel @Inject constructor(
    private val repository: ExternalRepository,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonIndex = mutableStateOf("")
    val pokemonIndex: State<String> = _pokemonIndex

    private val _dbIndex = mutableStateOf("")
    val dbIndex: State<String> = _dbIndex

    private val _generate = mutableStateOf("")
    val generate: State<String> = _generate

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _message = mutableStateOf("")
    val message: State<String> = _message

    private val _pokemonInfo = mutableStateOf(PokemonInfo())
    val pokemonInfo: State<PokemonInfo> = _pokemonInfo

    /** 포켓몬 조회 **/
    fun searchPokemon() {
        repository
            .pokemonInfo(_pokemonIndex.value.toInt())
            .onStart { _isLoading.value = true }
            .onEach { _pokemonInfo.value = it }
            .onCompletion { _isLoading.value = false }
            .catch { _message.value = it.message ?: "조회 오류" }
            .launchIn(viewModelScope)
    }

    /** 내용 업데이트 **/
    fun updateValue(type: String, value: String) {
        when (type) {
            Index -> {
                if (isNumeric(value) && value.length <= 6) {
                    _pokemonIndex.value = value
                }
            }
            DbIndex -> {
                val newValue = value.replace('.', '-')
                if (newValue.length <= 6) {
                    _dbIndex.value = newValue
                }
            }
            Generate -> {
                _generate.value = value
            }
            Name -> {
                _pokemonInfo.value = _pokemonInfo.value.copy(name = value)
            }
            Attribute -> {
                _pokemonInfo.value = _pokemonInfo.value.copy(attribute = value)
            }
            Characteristic -> {
                _pokemonInfo.value = _pokemonInfo.value.copy(characteristic = value)
            }
            Classification -> {
                _pokemonInfo.value = _pokemonInfo.value.copy(classification = value)
            }
            Description -> {
                _pokemonInfo.value = _pokemonInfo.value.copy(description = value)
            }
            Status -> {
                _pokemonInfo.value = _pokemonInfo.value.copy(status = value)
            }
        }
    }

    /** 포켓몬 등록 **/
    fun insertPokemon() = viewModelScope.launch {
        if (_dbIndex.value.isEmpty()) return@launch
        if (_generate.value.isEmpty()) return@launch
        if (_pokemonInfo.value.name.isEmpty()) return@launch

        val info = _pokemonInfo.value.copy(
            number = _dbIndex.value,
            generation = _generate.value.toInt()
        )

        _message.value = pokemonRepository.insertPokemon(info)
    }

    companion object {
        const val Index = "index"
        const val DbIndex = "dbIndex"
        const val Generate = "generate"
        const val Name = "name"
        const val Attribute = "attribute"
        const val Characteristic = "characteristic"
        const val Classification = "classification"
        const val Description = "description"
        const val Status = "status"
    }

}