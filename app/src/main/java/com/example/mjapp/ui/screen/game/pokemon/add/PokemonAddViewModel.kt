package com.example.mjapp.ui.screen.game.pokemon.add

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.PokemonEvolutionItem
import com.example.network.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonAddViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _evolutionList = mutableStateListOf(PokemonEvolutionItem())
    val evolutionList: List<PokemonEvolutionItem> = _evolutionList

    private val _status = MutableStateFlow<Status>(Status.Init)
    val status: Flow<Status> = _status

    fun addItem() {
        _evolutionList.add(PokemonEvolutionItem())
    }

    fun removeItem(index: Int) {
        _evolutionList.removeAt(index)
        if (_evolutionList.isEmpty()) {
            addItem()
        }
    }

    fun clearItems() {
        _evolutionList.clear()
        addItem()
    }

    fun updateNumberInfo(
        index: Int,
        isBeforeItem: Boolean,
        number: String,
        image: String
    ) {
        _evolutionList.getOrNull(index)?.let {
            if (isBeforeItem) {
                _evolutionList[index] = _evolutionList[index].copy(
                    beforeImage = image,
                    beforeNum = number
                )
            } else {
                _evolutionList[index] = _evolutionList[index].copy(
                    afterImage = image,
                    afterNum = number
                )
            }
        }
    }

    fun updateEvolutionType(
        index: Int,
        evolutionType: String,
    ) {
        _evolutionList.getOrNull(index)?.let {
            _evolutionList[index] = _evolutionList[index].copy(
                evolutionType = evolutionType,
            )
        }
    }

    fun updateEvolutionCondition(
        index: Int,
        evolutionCondition: String
    ) {
        _evolutionList.getOrNull(index)?.let {
            _evolutionList[index] = _evolutionList[index].copy(
                evolutionCondition = evolutionCondition
            )
        }
    }

    fun insertEvolution() = viewModelScope.launch {
        if (validationCheck()) {
            pokemonRepository.insertPokemonEvolution(
                evolutions = _evolutionList.map { it.toPokemonEvolution(getNumbersList()) },
                onSuccess = {
                    _status.value = Status.Success
                },
                onFailure = {
                    _status.value = Status.Failure("데이터 전송 중 오류가 발생하였습니다.")
                }
            )
        } else {
            _status.value = Status.Failure("데이터를 확인해주세요.")
        }
    }

    private fun validationCheck(): Boolean {
        _evolutionList.forEach {
            if (it.beforeNum.isEmpty() || it.afterNum.isEmpty() ||
                it.evolutionType.isEmpty() || it.evolutionCondition.isEmpty()
            ) return false
            if (it.beforeNum == it.afterNum) return false
        }

        return true
    }

    private fun getNumbersList(): String {
        val numberList = mutableListOf<String>()
        numberList.addAll(_evolutionList.map { it.afterNum })
        numberList.addAll(_evolutionList.map { it.beforeNum })

        return numberList
            .distinct()
            .sorted()
            .reduce { result, new ->
                "$result,$new"
            }
    }

    fun updateInitStatus() {
        _status.value = Status.Init
    }

    sealed class Status {
        object Init : Status()

        object Success: Status()

        data class Failure(val msg: String) : Status()
    }

}