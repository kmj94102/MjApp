package com.example.network.model

import java.text.SimpleDateFormat
import java.util.*

data class AccountBookItem(
    val id: Int = 0,
    val date: String,
    val dateOfWeek: String,
    val amount: Int,
    val usageType: String,
    val whereToUse: String
) {
    fun updateDate(dateValue: String): AccountBookItem {
        val sdfInput = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val sdfOutput = SimpleDateFormat("E", Locale.getDefault())

        val date = sdfInput.parse(dateValue) ?: return this

        return copy(
            date = dateValue,
            dateOfWeek = sdfOutput.format(date.time)
        )
    }

    fun checkValidity(): String = when {
        date.isEmpty() -> "날짜를 확인해 주세요"
        amount == 0 -> "금액을 확인해 주세요"
        usageType.isEmpty() -> "사용처를 선택해 주세요"
        whereToUse.isEmpty() -> "사용 내용을 입력해 주세요"
        else -> ""
    }

    fun uploadFormat(isIncome: Boolean) = copy(
        date = "${date.replace(".", "-")}T10:00:00.000Z",
        amount = if (isIncome) amount else amount * -1
    )

    companion object {
        fun initItem() = AccountBookItem(
            date = "",
            dateOfWeek = "",
            amount = 0,
            usageType = "",
            whereToUse = ""
        )
    }
}