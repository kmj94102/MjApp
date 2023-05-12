package com.example.network.model

import com.example.network.database.entity.ElswordCounterEntity

data class ElswordCounter(
    val id: Int,
    val name: String,
    val progress: Int,
    val completeMap: Map<String, Boolean>
) {
    companion object {
        fun fromCounterEntity(
            entity: ElswordCounterEntity
        ) = ElswordCounter(
            id = entity.id,
            name = entity.name,
            progress = entity.getProgress(),
            completeMap = entity.completeMap()
        )

        fun fromCounterEntityList(
            list: List<ElswordCounterEntity>
        ) = list.map { fromCounterEntity(it) }
    }
}
