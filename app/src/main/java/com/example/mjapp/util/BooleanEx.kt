package com.example.mjapp.util

fun isNumeric(str: String): Boolean {
    return str.matches("-?\\d+(\\.\\d+)?".toRegex())
}