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
            characters = characters?.toMutableList() ?: return null
        )
    }
}

data class ElswordQuestDetail(
    val id: Int,
    val name: String,
    val progress: Int,
    val characters: MutableList<ElswordCharacter>
) {
    fun getCharactersWithGroup() = characters.groupBy { it.group }
}

data class ElswordCharacter(
    val name: String,
    val image: String,
    val group: String,
    val isComplete: Boolean,
    val isOngoing: Boolean
) {
    fun updateCopy(type: String): ElswordCharacter {
        return when(type) {
            "complete" -> {
                this.copy(
                    isComplete = this.isComplete.not(),
                    isOngoing = false
                )
            }
            "ongoing" -> {
                this.copy(
                    isOngoing = this.isOngoing.not(),
                    isComplete = false
                )
            }
            else -> {
                this.copy(
                    isComplete = false,
                    isOngoing = false
                )
            }
        }
    }
}