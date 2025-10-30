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
    val name: String = "",
    val conditionInfo: List<ConditionInfo> = listOf(),
    val digimonInfo: List<DmoDigimonInfo> = listOf()
) {
    fun getProgress(conditionId: Int) = when (conditionId) {
        1 -> "보유 : ${digimonInfo.count { it.isOpen }} / ${digimonInfo.size}"
        2 -> {
            val totalLevel = conditionInfo.first { it.conditionId == conditionId }.conditionValue
            val reduceLevel = digimonInfo.filter { it.isOpen }.map { it.level }.reduce { acc, level -> acc + level }
            "레벨 총 합이 $reduceLevel / $totalLevel (평균 ${totalLevel / digimonInfo.size})"
        }
        3 -> "보유 : ${digimonInfo.count { it.isOpen && it.isTranscend }} / ${digimonInfo.size}"
        4 -> {
            val totalLevel = conditionInfo.first { it.conditionId == conditionId }.conditionValue
            val reduceLevel = digimonInfo.filter { it.isOpen }.map { it.level }.reduce { acc, level -> acc + level }
            "레벨 총 합이 $reduceLevel / $totalLevel (평균 ${totalLevel / digimonInfo.size})"
        }
        else -> ""
    }
}

data class ConditionInfo(
    val id: Int,
    val rewardId: Int,
    val rewardType: String,
    val rewardValue: Int,
    val conditionId: Int,
    val conditionType: String,
    val conditionValue: Int,
    val isComplete: Boolean?
) {
    fun getConditionInfo() = when (conditionId) {
        1 -> "${conditionValue}마리 보유 시, "
        2 -> "다지몬 레벨의 총 합이 $conditionValue 시,"
        3 -> "초월 디지몬 ${conditionValue}마리 보유시,"
        4 -> "디지몬 레벨의 총 합이 $conditionValue 시,"
        else -> ""
    } + "$rewardType + $rewardValue"
}

data class DmoDigimonInfo(
    val unionId: Int,
    val id: Int,
    val name: String,
    val level: Int,
    val image: String,
    val isOpen: Boolean,
    val isTranscend: Boolean
) {
    fun getStatus() = "Lv.${level} ${if (isTranscend) "| 초월" else ""}"
}