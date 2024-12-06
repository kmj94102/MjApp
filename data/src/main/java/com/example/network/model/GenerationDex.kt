package com.example.network.model

data class GenerationCount(
    val totalCount: Int,
    val isCatchCount: Int,
    val generationIdx: Int,
    val name: String
) {
    fun getCount() = "$isCatchCount / $totalCount"

    fun getImage(): String {

        val number = when(generationIdx) {
            101 -> "0001"
            401 -> "0387"
            402 -> "0493"
            801 -> "0810"
            802 -> "0891"
            803 -> "0898"
            901 -> "0906"
            902 -> "1017"
            903 -> "1024"
            else -> "0132"
        }

        return "https://firebasestorage.googleapis.com/v0/b/mbank-2a250.appspot.com/o/${number}.png?alt=media&token=d7d5689b-085f-4945-9ec6-6c61e94a4235"
    }

}

data class GenerationDex(
    val name: String,
    val number: String,
    val idx: Int,
    val generationIdx: Int,
    val isCatch: Boolean,
    val spotlight: String
)

fun List<GenerationDex>.getCatchStatus(): String {
    return "${count { it.isCatch }} / $size"
}

data class GenerationUpdateParam(
    val idx: Int
)