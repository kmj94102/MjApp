package com.example.network.service

import com.example.network.model.Persona3Community
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.Persona3Schedule
import com.example.network.model.Persona3ScheduleParam
import com.example.network.model.Persona3ScheduleUpdateParam
import retrofit2.http.Body
import retrofit2.http.POST

interface PersonaService {
    /** 페르소나3 스케줄 조회 **/
    @POST("/persona/3/select/schedule")
    suspend fun fetchPersona3Schedule(@Body item: Persona3ScheduleParam): List<Persona3Schedule>

    /** 페르소나3 스케줄 업데이트 **/
    @POST("/persona/3/update/schedule")
    suspend fun updatePersona3Schedule(@Body item: Persona3ScheduleUpdateParam): String

    /** 페르소나3 커뮤니티 조회 **/
    @POST("/persona/3/select/community")
    suspend fun fetchPersona3Community(): List<Persona3Community>

    /** 페르소나3 커뮤니티 업데이트 **/
    @POST("/persona/3/update/community")
    suspend fun updatePersona3Community(@Body item: Persona3CommunityUpdateParam): String
}