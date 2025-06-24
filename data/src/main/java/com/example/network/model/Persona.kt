package com.example.network.model

import com.example.network.database.entity.Persona3CommunitySelect

data class Persona3ScheduleParam(
    val skip: Int = 0,
    val limit: Int = 100
)

data class Persona3ScheduleUpdateParam(
    val idxList: List<Int> = emptyList()
)

data class Persona3CommunityUpdateParam(
    val idx: Int = 0,
    val rank: Int = 0
)

data class Persona3Schedule(
    val idx: Int,
    val dayOfWeek: String,
    val contents: String,
    val isComplete: Boolean,
    val day: Int,
    val month: Int,
    val title: String,
    val rank: Int?,
    val communityIdx: Int?
) {
    fun getDayInfo() = "${day.toString().padStart(2, '0')}일 $dayOfWeek"
}

fun List<Persona3Schedule>.getUpdateCommunityParam() = runCatching {
    this
        .filter { it.communityIdx != null && it.rank != null }
        .map {
            Persona3CommunityUpdateParam(
                idx = it.communityIdx!!,
                rank = it.rank!!
            )
        }
}.getOrDefault(emptyList())

data class Persona3Community(
    val image: String,
    val idx: Int,
    val arcana: String,
    val name: String,
    val rank: Int,
    val selectRank: Int = rank + 1
)

data class Persona3CommunityResult(
    val image: String,
    val idx: Int,
    val arcana: String,
    val name: String,
    val rank: Int,
    val selectRank: Int,
    val list: List<Persona3CommunitySelect>
) {
    companion object {
        fun from(item: Persona3Community, list: List<Persona3CommunitySelect>) =
            Persona3CommunityResult(
                image = item.image,
                idx = item.idx,
                arcana = item.arcana,
                name = item.name,
                rank = item.rank,
                selectRank = item.selectRank,
                list = list
            )
    }
}