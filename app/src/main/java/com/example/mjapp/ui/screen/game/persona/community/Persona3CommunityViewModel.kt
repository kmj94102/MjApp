package com.example.mjapp.ui.screen.game.persona.community

import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.Persona3CommunityResult
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.checkNetworkError
import com.example.network.repository.PersonaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class Persona3CommunityViewModel @Inject constructor(
    private val repository: PersonaRepository
) : BaseViewModel() {
    private val _list = MutableStateFlow<List<Persona3CommunityResult>>(emptyList())
    val list: StateFlow<List<Persona3CommunityResult>> = _list

    init {
        fetchPersona3Community()
    }

    private fun fetchPersona3Community() {
        repository
            .fetchPersona3Community()
            .onEach { _list.value = it }
            .catch {
                if (it.checkNetworkError()) {
                    updateNetworkErrorState()
                } else {
                    it.printStackTrace()
                    updateMessage(it.message ?: "오류 발생")
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateSelectRank(id: Int, selectRank: Int) {
        _list.update {
            it.map { item ->
                if (item.idx == id) item.copy(selectRank = selectRank)
                else item
            }
        }
    }

    fun updateRank(id: Int, rank: Int) {
        repository
            .updatePersona3Community(Persona3CommunityUpdateParam(id, rank))
            .onEach {
                _list.update {
                    it.map { item ->
                        if (item.idx == id) item.copy(rank = rank)
                        else item
                    }
                }
            }
            .catch {
                if (it.checkNetworkError()) {
                    updateNetworkErrorState()
                } else {
                    updateMessage(it.message ?: "오류 발생")
                }
            }
            .launchIn(viewModelScope)
    }
}