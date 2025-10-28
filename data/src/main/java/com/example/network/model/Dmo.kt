package com.example.network.model

data class DmoUnionInfo(
    val unionId: Int,
    val groupName: String,
    val isFavorite: Boolean,
    val conditions: List<Conditions>
) {
    fun getRewardInfo() =
        conditions.map {
            Pair("${it.rewardType} + ${it.rewardValue}", it.isComplete)
        }
}

data class Conditions(
    val id: Int,
    val rewardType: String,
    val rewardValue: Int,
    val conditionType: String,
    val conditionValue: Int,
    val isComplete: Boolean?
)

data class IdParam(
    val id: Int
)

data class DmoUnionDetail(
    val conditionInfo: List<ConditionInfo>,
    val digimonInfo: List<DmoDigimonInfo>
)

data class ConditionInfo(
    val id: Int,
    val rewardType: String,
    val rewardValue: Int,
    val conditionType: String,
    val conditionValue: Int
)

data class DmoDigimonInfo(
    val unionId: Int,
    val id: Int,
    val name: Int,
    val level: Int,
    val image: String,
    val isOpen: Boolean,
    val isTranscend: Boolean
)