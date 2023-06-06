package com.example.network.model

import com.google.gson.annotations.SerializedName

data class ElswordCounter(
    val id: Int,
    val name: String,
    val questId: Int,
    val progress: Int,
    val max: Int,
    val image: String,
    val characterGroup: String
)

data class ElswordCounterResult(
    val id: Int?,
    val name: String?,
    @SerializedName("quest_id")
    val questId: Int?,
    val progress: Int?,
    val max: Int?,
    val image: String?,
    val characterGroup: String?
) {
    fun toElswordCounter(): ElswordCounter? {
        return ElswordCounter(
            id = id ?: return null,
            name = name ?: return null,
            questId = questId ?: return null,
            progress = progress ?: return null,
            max = max ?: return null,
            image = image ?: return null,
            characterGroup = characterGroup ?: return null
        )
    }
}

data class ElswordCounterUpdateItem(
    val id: Int,
    val max: Int
)