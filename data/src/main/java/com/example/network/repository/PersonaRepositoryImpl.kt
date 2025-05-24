package com.example.network.repository

import com.example.network.model.Persona3Community
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.Persona3Schedule
import com.example.network.model.Persona3ScheduleParam
import com.example.network.model.Persona3ScheduleUpdateParam
import com.example.network.model.getFailureThrow
import com.example.network.service.PersonaClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PersonaRepositoryImpl @Inject constructor(
    private val client: PersonaClient
) : PersonaRepository {
    override fun fetchPersona3Schedule(item: Persona3ScheduleParam): Flow<Map<String, List<Persona3Schedule>>> =
        flow {
            client.fetchPersona3Schedule(item)
                .onSuccess {
                    emit(it.groupBy { "${it.month}.${it.day}" })
                }
                .getFailureThrow()
        }

    override fun updatePersona3Schedule(item: Persona3ScheduleUpdateParam): Flow<String> =
        flow {
            client.updatePersona3Schedule(item)
                .onSuccess { emit(it) }
                .getFailureThrow()
        }

    override fun fetchPersona3Community(): Flow<List<Persona3Community>> =
        flow {
            client.fetchPersona3Community()
                .onSuccess { emit(it) }
                .getFailureThrow()
        }

    override fun updatePersona3Community(item: Persona3CommunityUpdateParam): Flow<String> =
        flow {
            client.updatePersona3Community(item)
                .onSuccess { emit(it) }
                .getFailureThrow()
        }
}