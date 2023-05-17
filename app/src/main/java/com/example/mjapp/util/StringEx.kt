package com.example.mjapp.util

import java.text.SimpleDateFormat
import java.util.*

fun getToday(format: String = "yyyy.MM.dd"): String {
    val today = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format, Locale.KOREA)

    return formatter.format(today)
}