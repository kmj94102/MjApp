package com.example.mjapp.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

fun main() {
    val solution = Solution()
    val result = solution.solution(
        arrayOf(
            "2-M30915",
            "19-MO1299",
            "17-CO0901",
            "14-DE0511",
            "19-KE1102",
            "13-DE0101",
            "20-SP0404",
            "20-CO0794"
        )
    )

    result.forEach {
        print("$it,")
    }

}

class Solution {

    fun solution(assets: Array<String>): Array<String> =
        assets
            .mapNotNull { asset ->
                runCatching { asset.toAssetResult() }
                    .onFailure {
                        println("오류 발생\n${it.message} ")
                    }
                    .getOrNull()
            }
            .sorted()
            .filter(Asset::isValid)
            .map(Asset::text)
            .distinct()
            .toTypedArray()

}

object ValidationError : Throwable()


fun String.toAssetResult(): Asset =
    when {
        length != 9 -> throw Exception("자리수 부족")
        slice(0..1).toIntOrNull() == null -> throw ValidationError
        get(2) != '-' -> throw ValidationError
        runCatching { Asset.Type.valueOf(slice(3..4)) }.isFailure -> throw ValidationError
        slice(5..6).toIntOrNull() == null -> throw ValidationError
        slice(7..8).toIntOrNull() == null -> throw ValidationError
        else -> Asset(
            text = this,
            yy = slice(0..1).toInt(),
            type = Asset.Type.valueOf(slice(3..4)),
            mm = slice(5..6).toInt(),
            no = slice(7..8).toInt()
        )
    }

data class Asset(
    val text: String,
    val yy: Int,
    val type: Type,
    val mm: Int,
    val no: Int
) : Comparable<Asset> {

    enum class Type {
        SP, KE, MO, CO, DE
    }

    val isValid: Boolean
        get() {
            return (yy in 13..22) && (mm in 1..12) && when {
                yy == 13 && mm < 4 -> false
                yy == 22 && mm > 8 -> false
                else -> true
            } && (no in 1..99)
        }

    override fun compareTo(other: Asset): Int {
        return when {
            yy > other.yy -> 1
            yy < other.yy -> -1
            type.ordinal > other.type.ordinal -> 1
            type.ordinal < other.type.ordinal -> -1
            mm > other.mm -> 1
            mm < other.mm -> -1
            no > other.no -> 1
            no < other.no -> -1
            else -> 0
        }
    }

}
