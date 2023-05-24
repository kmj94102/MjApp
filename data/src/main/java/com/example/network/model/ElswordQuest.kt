package com.example.network.model

data class ElswordQuest(
    val name: String,
    val max: Int,
    val complete: String = "",
    val ongoing: String = ""
)

data class ElswordQuestSimpleResult(
    val progress: Float?,
    val id: Int?,
    val name: String?
) {
    fun toElswordQuestSimple(): ElswordQuestSimple? {
        return ElswordQuestSimple(
            progress = progress ?: return null,
            id = id ?: return null,
            name = name ?: return null,
        )
    }
}

data class ElswordQuestSimple(
    val progress: Float,
    val id: Int,
    val name: String
)