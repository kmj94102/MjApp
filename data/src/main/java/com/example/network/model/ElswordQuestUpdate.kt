package com.example.network.model

data class ElswordQuestUpdate(
    val id: Int,
    val name: String,
    val type: String
) {
    companion object {
        const val Complete = "complete"
        const val Ongoing = "ongoing"
        const val Remove = "remove"
    }
}
