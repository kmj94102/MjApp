package com.example.network.repository

import com.example.network.database.entity.Persona3Quest
import com.example.network.model.Persona3Community
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.Persona3Schedule
import com.example.network.model.Persona3ScheduleParam
import com.example.network.model.Persona3ScheduleUpdateParam
import kotlinx.coroutines.flow.Flow

interface PersonaRepository {
    /** 페르소나3 스케줄 조회 **/
    fun fetchPersona3Schedule(item: Persona3ScheduleParam):  Flow<Map<String, List<Persona3Schedule>>>

    /** 페르소나3 스케줄 업데이트 **/
    fun updatePersona3Schedule(item: Persona3ScheduleUpdateParam): Flow<String>

    /** 페르소나3 커뮤니티 조회 **/
    fun fetchPersona3Community(): Flow<List<Persona3Community>>

    /** 페르소나3 커뮤니티 업데이트 **/
    fun updatePersona3Community(item: Persona3CommunityUpdateParam): Flow<String>

    /** 페르소나3 퀘스트 조회 **/
    fun fetchPersona3Quest(): Flow<List<Persona3Quest>>

    /** 페르소나3 퀘스트 등록 **/
    suspend fun insertPersona3Quest(): Boolean

    /** 페르소나3 퀘스트 업데이트 **/
    fun updatePersona3Quest(id: Int): Flow<String>
}