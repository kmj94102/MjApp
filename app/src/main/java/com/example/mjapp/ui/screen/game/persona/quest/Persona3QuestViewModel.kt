package com.example.mjapp.ui.screen.game.persona.quest

import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.repository.PersonaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Persona3QuestViewModel @Inject constructor(
    private val repository: PersonaRepository
): BaseViewModel() {

    val list = repository
        .fetchPersona3Quest()
        .onEach { if (it.isEmpty()) repository.insertPersona3Quest() }
        .catch {
            it.printStackTrace()
            it.message?.let { updateMessage(it) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updatePersona3Quest(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository
            .updatePersona3Quest(id)
            .onSuccess { updateMessage("퀘스트를 완료했습니다.") }
            .onFailure {
                it.printStackTrace()
                updateMessage("퀘스트 완료에 실패했습니다.") }
    }

}