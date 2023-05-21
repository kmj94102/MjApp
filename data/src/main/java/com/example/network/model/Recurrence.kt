package com.example.network.model

enum class Recurrence(val originName: String, val koreanName: String) {
    Daily("daily", "매일"),
    Weekly("weekly", "매주"),
    Monthly("monthly", "매월"),
    Yearly("yearly", "매년"),
    NoRecurrence("none", "반복 안함");

    companion object {
        fun getRecurrenceState(name: String): Recurrence =
            Recurrence.values().find { it.originName == name } ?: NoRecurrence

        fun getRecurrenceKoreanName(name: String): String =
            Recurrence.values().find { it.originName == name }?.koreanName ?: NoRecurrence.koreanName
    }
}