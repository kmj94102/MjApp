package com.example.network.service

import com.example.network.database.dao.PersonaDao
import com.example.network.database.entity.createPersona3QuestList
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.Persona3ScheduleParam
import com.example.network.model.Persona3ScheduleUpdateParam
import javax.inject.Inject

class PersonaClient @Inject constructor(
    private val service: PersonaService,
    private val dao: PersonaDao
) {
    /** 페르소나3 스케줄 조회 **/
    suspend fun fetchPersona3Schedule(item: Persona3ScheduleParam) = runCatching {
        service.fetchPersona3Schedule(item)
    }
    /** 페르소나3 스케줄 업데이트 **/
    suspend fun updatePersona3Schedule(item: Persona3ScheduleUpdateParam) = runCatching {
        service.updatePersona3Schedule(item)
    }
    /** 페르소나3 커뮤니티 조회 **/
    suspend fun fetchPersona3Community() = runCatching {
        service.fetchPersona3Community()
    }
    /** 페르소나3 커뮤니티 업데이트 **/
    suspend fun updatePersona3Community(item: Persona3CommunityUpdateParam) = runCatching {
        service.updatePersona3Community(item)
    }
    /** 페르소나3 퀘스트 조회 **/
    fun fetchPersona3Quest() = dao.fetchPersona3Quest()
    /** 페르소나3 퀘스트 등록 **/
    suspend fun insertPersona3Quest() = runCatching {
        dao.insertPersona3Quest(createPersona3QuestList())
    }
    /** 페르소나3 퀘스트 업데이트 **/
    fun updatePersona3Quest(id: Int) = runCatching {
        dao.updatePersona3Quest(id, true)
    }
}