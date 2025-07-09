package com.example.network.util

import java.text.NumberFormat

fun Int.amountFormat(): String = NumberFormat.getNumberInstance().format(this)

fun Int.priceFormat(): String = "${this.amountFormat()}원"