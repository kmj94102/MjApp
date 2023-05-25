package com.example.network.model

import com.google.gson.annotations.SerializedName

data class ElswordQuestDetailResult(
    val id: Int?,
    val name: String?,
    val progress: Float?,
    @SerializedName("character")
    val characters: List<ElswordCharacter>?
) {
    fun toElswordQuestDetail(): ElswordQuestDetail? {
        return ElswordQuestDetail(
            id = id ?: return null,
            name = name ?: return null,
            progress = progress?.toInt() ?: return null,
            characters = characters?.groupBy { it.group } ?: return null
        )
    }
}

data class ElswordQuestDetail(
    val id: Int,
    val name: String,
    val progress: Int,
    val characters: Map<String, List<ElswordCharacter>>
)

data class ElswordCharacter(
    val name: String,
    val image: String,
    val group: String,
    val isComplete: Boolean,
    val isOngoing: Boolean
)