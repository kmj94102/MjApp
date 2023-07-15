package com.example.mjapp.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun getToday(format: String = "yyyy.MM.dd"): String {
    val today = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format, Locale.KOREA)

    return formatter.format(today)
}

fun makeRouteWithArgs(route: String, vararg args: String): String = buildString {
    append(route)
    args.forEach {
        append("/$it")
    }
}

fun Calendar.toStringFormat(): String {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    return formatter.format(time)
}

fun Int.formatAmount(): String = NumberFormat.getNumberInstance().format(abs(this))

fun String.removeNumberFormat() =
    replace(",", "").replace("만", "").replace("원", "").trim()


fun Int.formatAmountWithSign(): String {
    val format = formatAmount()

    return when {
        this > 0 -> "+ $format 원"
        this < 0 -> "- $format 원"
        else -> format.plus(" 원")
    }
}

fun Int.formatAmountInTenThousand(): String {
    val format = (abs(this) / 10_000).formatAmount()

    return when {
        this > 0 -> "+ $format 만원"
        this < 0 -> "- $format 만원"
        else -> format.plus(" 원")
    }
}