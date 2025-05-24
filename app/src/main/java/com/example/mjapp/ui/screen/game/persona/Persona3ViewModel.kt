package com.example.mjapp.ui.screen.game.persona

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.Persona3Schedule
import com.example.network.model.Persona3ScheduleParam
import com.example.network.model.Persona3ScheduleUpdateParam
import com.example.network.repository.PersonaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class Persona3ViewModel @Inject constructor(
    private val repository: PersonaRepository
) : BaseViewModel() {

    private val _info = MutableStateFlow<Map<String, List<Persona3Schedule>>>(mapOf())
    val info: StateFlow<Map<String, List<Persona3Schedule>>> = _info

    init {
        fetchSchedule()
    }

    fun fetchSchedule() {
        repository
            .fetchPersona3Schedule(Persona3ScheduleParam(skip = 0, limit = 100))
            .onEach {
                _info.value = _info.value.toMutableMap().apply { this.putAll(it) }
            }
            .catch {
                if (it is NetworkErrorException) updateNetworkErrorState()
                else updateMessage(it.message ?: "")
            }
            .launchIn(viewModelScope)
    }

    fun updateSchedule(
        key: String,
        schedulePrams: List<Int>,
        communityPrams: List<Persona3CommunityUpdateParam>
    ) {
        repository
            .updatePersona3Schedule(Persona3ScheduleUpdateParam(schedulePrams))
            .onEach {
                communityPrams.forEach { updateCommunity(it) }
                _info.value = _info.value.toMutableMap().apply { this.remove(key) }
            }
            .catch { updateMessage(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    private fun updateCommunity(item: Persona3CommunityUpdateParam) {
        repository
            .updatePersona3Community(item = item)
            .onEach { Log.d("updateCommunity", it) }
            .catch { updateMessage(it.message ?: "") }
            .launchIn(viewModelScope)
    }

}